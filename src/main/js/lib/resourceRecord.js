/*
 * This file was last modified at 2021.03.21 13:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * resourceRecord.js
 * $Id$
 */

import postTags from "./postTags";
import urlMethod from "./urlMethod";
import {API_V1_RESOURCE_RECORD} from "../config/api";
import {resourceRecordError, resourceRecordPending} from '../redux/resourceRecord';

export const postArticleRecord = (value, f) => resourceRecord('POST', 'article', value, f);
export const putArticleRecord = (value, f) => resourceRecord('PUT', 'article', value, f);
export const postNewsEntryRecord = (value, f) => resourceRecord('POST', 'news-entry', value, f);
export const putNewsEntryRecord = (value, f) => resourceRecord('PUT', 'news-entry', value, f);

function postTagsCall(dispatch, data, tags, f) {
    console.log("postTagsCall(" + JSON.stringify(data) + ', ' + JSON.stringify(tags) + ")");
    if (data.error) {
        throw(data.error);
    }
    if ('success' === data.status) {
        console.log("postTagsCall => postTags");
        postTags(dispatch, {
            id: data.object.id,
            tags: tags,
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
                .then(data => postTagsCall(dispatch, data, value.tags, f))
                .catch(error => dispatch(resourceRecordError(error)));
        }
    }
}
