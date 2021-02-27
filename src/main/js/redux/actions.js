/*
 * This file was last modified at 2021.02.27 11:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * actions.js
 * $Id$
 */

import {API_V1_RESOURCE_RECORD} from '../config/api';
import {RT_COOKIE_OPTIONS, RT_TTL} from '../config/consts';
import Cookies from 'js-cookie';

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
                    if (data['rt'] !== undefined) {
                        let date = new Date(Date.now() + RT_TTL);
                        date = date.toUTCString();
                        const options = {
                            ...RT_COOKIE_OPTIONS,
                            expires: date
                        };
                        Cookies.set('rt', data.rt, options);
                    }
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
                    if (data.rt) {
                        let date = new Date(Date.now() + RT_TTL);
                        date = date.toUTCString();
                        const options = {
                            ...RT_COOKIE_OPTIONS,
                            expires: date
                        };
                        Cookies.set('rt', data.rt, options);
                    }
                    window.sessionStorage.setItem("token", data.token)
                    dispatch(loginUser(data.user))
                }
            })
    }
}

export const adminCreateArticle = (value) => {
    return resourceRecord('POST', 'article', value);
}

export const adminUpdateArticle = (value) => {
    return resourceRecord('PUT', 'article', value);
}

export const adminCreateNewsEntry = value => {
    return resourceRecord('POST', 'news-entry', value);
}

export const adminUpdateNewsEntry = value => {
    return resourceRecord('PUT', 'news-entry', value);
}

export const adminCreateNewsLinks = value => {
    return resourceRecord('POST', 'news-links', value);
}

export const adminUpdateNewsLinks = value => {
    return resourceRecord('PUT', 'news-links', value);
}

export const resourceRecord = (method, object, value) => {
    return dispatch => {
        const token = window.sessionStorage.token;
        if (token) {
            return fetch(API_V1_RESOURCE_RECORD + '/' + object, {
                method: method,
                headers: {
                    'Content-Type': 'application/json',
                    Accept: 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(value)
            })
                .then(resp => resp.json())
                .then(data => {
                    if (data.message) {
                        //тут ваша логика
                    } else {
                        console.log('resourceRecord(' + method + ',' + object + ', value) data: ');
                        console.log(data);
                    }
                })
        }
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

export const setLocale = (locale = '') => ({type: 'LOCALE', payload: locale});
