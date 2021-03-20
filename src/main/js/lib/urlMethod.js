/*
 * This file was last modified at 2021.03.20 20:43 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * urlMethod.js
 * $Id$
 */

export default function urlMethod(url, method, object, value, token) {
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
