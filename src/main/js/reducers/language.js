/*
 * This file was last modified at 2021.03.02 23:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * language.js
 * $Id$
 */

const now = new Date();

const initialState = {
    language: 'ru'
}

export default function language(state = initialState, action) {
    switch (action.type) {
        case "SET_LANGUAGE":
            return state.merge(action.state);
        case 'UPDATED_LANGUAGE':
            return {...state, language: action.payload}
        default:
            return state;
    }
}
