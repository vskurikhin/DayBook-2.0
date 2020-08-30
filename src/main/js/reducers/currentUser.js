/*
 * This file was last modified at 2020.08.30 17:29 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * currentUser.js
 * $Id$
 */

const initialState = {
    currentUser: {}
}

export default function currentUser(state = initialState, action) {
    switch (action.type) {
        case 'LOGIN_USER':
            return {...state, currentUser: action.payload}
        default:
            return state;
    }
}

