/*
 * This file was last modified at 2021.03.23 09:40 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * resourceRecord.js
 * $Id$
 */

import urlMethod from "./urlMethod";
import {API_V1_RESOURCE_RECORD} from "../config/api";
import {postTagsCall} from "./postTags";
import {resourceRecordError, resourceRecordPending} from '../redux/resourceRecord';

export const postArticleRecord = (value, f) => resourceRecord('POST', 'article', value, f);
export const putArticleRecord = (value, f) => resourceRecord('PUT', 'article', value, f);
export const postNewsEntryRecord = (value, f) => resourceRecord('POST', 'news-entry', value, f);
export const putNewsEntryRecord = (value, f) => resourceRecord('PUT', 'news-entry', value, f);

function checkPostTags(dispatch, data, tags, f) {
    if (data.error) {
        throw(data.error);
    }
    if ('success' === data.status) {
        postTagsCall(dispatch, {
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
            dispatch(resourceRecordPending());
            return urlMethod(API_V1_RESOURCE_RECORD, method, object, value, token)
                .then(data => data.json())
                .then(data => checkPostTags(dispatch, data, value.tags, f))
                .catch(error => dispatch(resourceRecordError(error)));
        }
    }
}
