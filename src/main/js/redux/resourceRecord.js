/*
 * This file was last modified at 2021.03.21 13:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * resourceRecord.js
 * $Id$
 */

export const RESOURCE_RECORD_PENDING = 'RESOURCE_RECORD_PENDING';
export const RESOURCE_RECORD_SUCCESS = 'RESOURCE_RECORD_SUCCESS';
export const RESOURCE_RECORD_ERROR = 'RESOURCE_RECORD_ERROR';

export function resourceRecordPending() {
    return {
        type: RESOURCE_RECORD_PENDING
    }
}

export function resourceRecordSuccess(value) {
    return {
        type: RESOURCE_RECORD_SUCCESS,
        resourceRecord: value
    }
}

export function resourceRecordError(error) {
    return {
        type: RESOURCE_RECORD_ERROR,
        error: error
    }
}
