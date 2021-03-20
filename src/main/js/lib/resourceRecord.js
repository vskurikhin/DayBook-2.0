/*
 * This file was last modified at 2021.03.20 15:52 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * resourceRecord.js
 * $Id$
 */

import {resourceRecordPending, resourceRecordSuccess, resourceRecordError} from '../redux/resourceRecordActions';
import {API_V1_RESOURCE_RECORD} from "../config/api";

function dispatchResourceRecord(dispatch, res, f) {
    if (res.error) {
        throw(res.error);
    }
    dispatch(resourceRecordSuccess(res.object));
    dispatch(f(res));

    return res.object;
}

export const postNewsEntryRecord = (value, f) => resourceRecord('POST', 'news-entry', value, f);
export const putNewsEntryRecord = (value, f) => resourceRecord('PUT', 'news-entry', value, f);

export function resourceRecord(method, object, value, f) {
    return dispatch => {
        const token = window.sessionStorage.token;
        if (token) {
            console.log("resourceRecord(" + object + ', ' + JSON.stringify(value) + ")")
            dispatch(resourceRecordPending());
            urlMethod(API_V1_RESOURCE_RECORD, method, object, value, token)
                .then(res => res.json())
                .then(res => dispatchResourceRecord(dispatch, res, f))
                .catch(error => dispatch(resourceRecordError(error)));
        }
    }
}

export function urlMethod(url, method, object, value, token) {
    return fetch(url + '/' + object, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(value)
    })
}

export default resourceRecord;