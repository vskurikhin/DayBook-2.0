/*
 * This file was last modified at 2021.02.03 18:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * currentDate.js
 * $Id$
 */

const now = new Date();

const initialState = {
    currentDate: {"year":now.getFullYear(),"month":now.getMonth()+1,"date":now.getDay()}
}

export default function currentDate(state = initialState, action) {
    switch (action.type) {
        case "SET_STATE":
            return state.merge(action.state);
        case 'CALENDAR_DATE':
            return {...state, currentDate: action.payload}
        default:
            return state;
    }
}
