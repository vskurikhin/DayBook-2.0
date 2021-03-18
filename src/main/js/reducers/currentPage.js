/*
 * This file was last modified at 2021.02.03 18:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * currentPage.js
 * $Id$
 */

const initialState = {
    currentPage: {first: 0, page: 0}
}

export default function currentPage(state = initialState, action) {
    switch (action.type) {
        case 'SET_PAGE':
            return state.merge(action.state);
        case 'UPDATED_PAGE':
            return {...state, currentPage: action.payload}
        default:
            return state;
    }
}
