/*
 * This file was last modified at 2021.02.24 00:07 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * reducerRecord.js
 * $Id$
 */

export const defaultRecordState = {
    responseData: null,
    isFetching: true,
    error: null
};

export default function reducerRecord(state = defaultRecordState, action) {
    switch (action.type) {
        case "fetched":
            return {
                ...state,
                isFetching: false,
                responseData: action.payload
            };
        case "error":
            return {
                ...state,
                isFetching: false,
                error: action.payload
            };
        default:
            return state;
    }
}