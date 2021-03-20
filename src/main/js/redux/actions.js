/*
 * This file was last modified at 2021.03.20 20:43 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * actions.js
 * $Id$
 */

import {API_V1_RESOURCE, API_V1_RESOURCE_RECORD} from '../config/api';

export const createNewsGroup = value => resource('POST', 'news-group', value);

export const updateNewsGroup = value => resource('PUT', 'news-group', value);

export const createTagLabel = value => resource('POST', 'tag-label', value);

export const updateTagLabel = value => resource('PUT', 'tag-label', value);

export const createArticle = value => resourceRecord('POST', 'article', value);

export const updateArticle = value => resourceRecord('PUT', 'article', value);

export const createNewsLinks = value => resourceRecord('POST', 'news-links', value);

export const updateNewsLinks = value => resourceRecord('PUT', 'news-links', value);

export const logMessage = (label, message) => {
    console.log(label);
    console.log(message);
}

export const resourceRecord = (method, object, value) => {
    return dispatch => {
        const token = window.sessionStorage.token;
        if (token) {
            return urlMethod(API_V1_RESOURCE_RECORD, method, object, value, token)
                .then(resp => resp.json())
                .then(data => {
                    if (data.message) {
                        logMessage('resourceRecord message: ', data.message);
                        // noinspection EqualityComparisonWithCoercionJS
                        if ('success' == data.status) {
                            dispatch(resourceTags({
                                ...value,
                                id: data.object.id
                            }));
                        }
                    } else {
                        logMessage('resourceRecord data: ', data);
                    }
                })
        }
    }
}

export const resource = (method, object, value) => {
    return dispatch => {
        const token = window.sessionStorage.token;
        if (token) {
            console.log("resourceTags(" + JSON.stringify(value) + ")")
            return urlMethod(API_V1_RESOURCE, method, object, value, token)
                .then(resp => resp.json())
                .then(data => {
                    if (data.message) {
                        logMessage('resource message: ', data.message);
                    } else {
                        logMessage('resource data: ', data);
                    }
                })
        }
    }
}

export const resourceTags = (value, props) => {
    return dispatch => {
        const token = window.sessionStorage.token;
        if (token) {
            console.log("resourceTags(" + JSON.stringify(value) + ")")
            return urlMethod(API_V1_RESOURCE, 'POST', 'add-tags', value, token)
                .then(resp => resp.json())
                .then(data => {
                    if (data.message) {
                        logMessage('resourceTags message: ', data.message);
                        // noinspection EqualityComparisonWithCoercionJS
                        if ('success' == data.status) {
                            console.log(data.object);
                            dispatch(setUpdatedRecord(data.object));
                        }
                    } else {
                        logMessage('resourceTags data: ', data);
                    }
                })
        }
    }
}

export const urlMethod = (url, method, object, value, token) => {
    return fetch(url + '/' + object, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(value)
    })
}


export const userPostFetch = user => {

    return dispatch => {
        return fetch("/api/v1/users", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
            },
            body: JSON.stringify({user})
        })
            .then(resp => resp.json())
            .then(data => {
                if (data.message) {
                    //Тут прописываем логику
                } else {
                    window.sessionStorage.setItem("token", data.jwt)
                    dispatch(loginUser(data.user))
                }
            })
    }
}

export const userLoginFetch = user => {
    return dispatch => {
        console.log('user: ' + JSON.stringify(user))
        return fetch("/api/v1/auth/login", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
            },
            body: JSON.stringify(user)
        })
            .then(resp => resp.json())
            .then(data => {
                if (data.message) {
                    //тут ваша логика
                } else {
                    window.sessionStorage.setItem("token", data.token)
                    dispatch(loginUser(data.user))
                }
            })
    }
}

export const getProfileFetch = () => {
    return dispatch => {
        const token = window.sessionStorage.token;
        if (token) {
            return fetch("/api/v1/resource/profile", {
                method: "GET",
                headers: {
                    'Content-Type': 'application/json',
                    Accept: 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            })
                .then(resp => resp.json())
                .then(data => {
                    if (data.message) {
                        // Будет ошибка если token не дествительный
                        window.sessionStorage.removeItem("token")
                    } else {
                        dispatch(loginUser(data.user))
                    }
                })
        }
    }
}

export const loginUser = userObj => ({
    type: 'LOGIN_USER',
    payload: userObj
})

export const logoutUser = () => ({
    type: 'LOGOUT_USER'
})

const now = new Date();

export const setCalendarDate = (date = {
    "year": now.getFullYear(),
    "month": now.getMonth() + 1,
    "date": now.getDay()
}) => ({type: 'CALENDAR_DATE', payload: date});


export const setLanguage = (value = {}) => ({type: 'UPDATED_LANGUAGE', payload: value});

export const setUpdatedRecord = (value = {}) => ({type: 'UPDATED_RECORD', payload: value});

export const setLocale = (locale = '') => ({type: 'LOCALE', payload: locale});
