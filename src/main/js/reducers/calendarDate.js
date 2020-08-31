/*
 * This file was last modified at 2020.08.31 14:07 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * currentUser.js
 * $Id$
 */

const initialState = {
    calendarDate: {}
}

export default function calendarDate(state = initialState, action) {
    switch (action.type) {
        case 'CALENDAR_DATE':
            return {...state, calendarDate: action.payload}
        default:
            return state;
    }
}
