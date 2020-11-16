/*
 * This file was last modified at 2020.11.15 22:00 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * actions.js
 * $Id$
 */

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
                    localStorage.setItem("token", data.jwt)
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
                    localStorage.setItem("token", data.token)
                    dispatch(loginUser(data.user))
                }
            })
    }
}

export const getProfileFetch = () => {
    return dispatch => {
        const token = localStorage.token;
        if (token) {
            return fetch("/api/v1/profile", {
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
                        localStorage.removeItem("token")
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

export const setCalendarDate = (date = {"year":2020,"month":1,"date":30}) => ({type: 'CALENDAR_DATE', payload: date});
export const setLocale = (locale = '') => ({type: 'LOCALE', payload: locale});
