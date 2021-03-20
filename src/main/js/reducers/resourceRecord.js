/*
 * This file was last modified at 2021.03.20 15:52 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * resourceRecord.js
 * $Id$
 */

import {RESOURCE_RECORD_PENDING, RESOURCE_RECORD_SUCCESS, RESOURCE_RECORD_ERROR} from '../redux/resourceRecordActions';

const initialState = {
    pending: false,
    resourceRecord: {},
    error: null
}

export default function resourceRecord(state = initialState, action) {
    switch(action.type) {
        case RESOURCE_RECORD_PENDING:
            return {
                ...state,
                pending: true
            }
        case RESOURCE_RECORD_SUCCESS:
            return {
                ...state,
                pending: false,
                resourceRecord: action.payload
            }
        case RESOURCE_RECORD_ERROR:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        default:
            return state;
    }
}

export const getResourceRecord = state => state.resourceRecord;
export const getResourceRecordPending = state => state.pending;
export const getResourceRecordError = state => state.error;
