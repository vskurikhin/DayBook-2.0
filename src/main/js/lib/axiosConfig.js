/*
 * This file was last modified at 2021.02.03 21:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * axiosConfig.js
 * $Id$
 */


export const getConfigHeadersAuthorization = (token) => {
    if (typeof token !== 'undefined') {
        return {
            headers: {Authorization: `Bearer ${token}`}
        }
    }
    return null;
}
