/*
 * This file was last modified at 2021.03.20 20:43 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * resourceRecord.js
 * $Id$
 */

import postTags from "./postTags";
import urlMethod from "./urlMethod";
import {API_V1_RESOURCE_RECORD} from "../config/api";
import {resourceRecordError, resourceRecordPending, resourceRecordSuccess} from '../redux/resourceRecordActions';

export const postNewsEntryRecord = (value, f) => resourceRecord('POST', 'news-entry', value, f);
export const putNewsEntryRecord = (value, f) => resourceRecord('PUT', 'news-entry', value, f);

function thenResourceRecordCall(dispatch, data, tags, f) {
    console.log("thenResourceRecordCall(" + JSON.stringify(data) + ', ' + JSON.stringify(tags) + ")");
    if (data.error) {
        throw(data.error);
    }
    if ('success' === data.status) {
        console.log("thenResourceRecordCall => postTags");
        postTags(dispatch, {
            tags: tags,
            id: data.object.id
        }, f);
    }

    return data.object;
}

export default function resourceRecord(method, object, value, f) {
    return dispatch => {
        const token = window.sessionStorage.token;
        if (token) {
            console.log("resourceRecord(" + object + ', ' + JSON.stringify(value) + ")")
            dispatch(resourceRecordPending());
            return urlMethod(API_V1_RESOURCE_RECORD, method, object, value, token)
                .then(data => data.json())
                .then(data => thenResourceRecordCall(dispatch, data, value.tags, f))
                .catch(error => dispatch(resourceRecordError(error)));
        }
    }
}
