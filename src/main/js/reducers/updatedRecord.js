/*
 * This file was last modified at 2021.02.28 23:25 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * updatedRecord.js
 * $Id$
 */

const initialState = {
    updatedRecord: {}
}

export default function updatedRecord(state = initialState, action) {
    switch (action.type) {
        case "SET_STATE":
            return state.merge(action.state);
        case 'UPDATED_RECORD':
            return {...state, updatedRecord: action.payload}
        default:
            return state;
    }
}
