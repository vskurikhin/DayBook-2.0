/*
 * This file was last modified at 2021.02.28 23:25 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * actions.js
 * $Id$
 */

import {API_V1_RESOURCE, API_V1_RESOURCE_RECORD} from '../config/api';

export const createArticle = value => resourceRecord('POST', 'article', value);

export const updateArticle = value => resourceRecord('PUT', 'article', value);

export const createNewsEntry = value => resourceRecord('POST', 'news-entry', value);

export const updateNewsEntry = value => resourceRecord('PUT', 'news-entry', value);

export const createNewsLinks = value => resourceRecord('POST', 'news-links', value);

export const updateNewsLinks = value => resourceRecord('PUT', 'news-links', value);

export const resourceRecord = (method, object, value) => {
    return dispatch => {
        const token = window.sessionStorage.token;
        if (token) {
            return resource(API_V1_RESOURCE_RECORD, method, object, value, token)
                .then(resp => resp.json())
                .then(data => {
                    if (data.message) {
                        console.log('resourceRecord message: ');
                        console.log(data.message);
                        // noinspection EqualityComparisonWithCoercionJS
                        if ('success' == data.status) {
                            dispatch(resourceTags({
                                ...value,
                                id: data.object.id
                            }));
                        }
                    } else {
                        console.log('resourceRecord data: ');
                        console.log(data);
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
            return resource(API_V1_RESOURCE, 'POST', 'add-tags', value, token)
                .then(resp => resp.json())
                .then(data => {
                    if (data.message) {
                        console.log('resourceTags message: ');
                        console.log(data.message);
                        // noinspection EqualityComparisonWithCoercionJS
                        if ('success' == data.status) {
                            console.log(data.object);
                            dispatch(setUpdatedRecord(data.object));
                        }
                    } else {
                        console.log('resourceTags data: ');
                        console.log(data);
                    }
                })
        }
    }
}

export const resource = (url, method, object, value, token) => {
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


export const setUpdatedRecord = (value = {}) => ({type: 'UPDATED_RECORD', payload: value});

export const setLocale = (locale = '') => ({type: 'LOCALE', payload: locale});
