/*
 * This file was last modified at 2021.03.21 17:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * postTags.js
 * $Id$
 */

import urlMethod from "./urlMethod";
import {API_V1_RESOURCE} from "../config/api";
import {resourceRecordError} from "../redux/resourceRecord";

function dispatchFCall(dispatch, data, f) {
    console.log("dispatchFCall(" + data + ")")
    if (data.error) {
        throw(data.error);
    }
    dispatch(f(data.object));

    return data.object;
}

export function postTagsCall(dispatch, value, f) {
    console.log("postTagsCall(" + value + ")")
    const token = window.sessionStorage.token;
    if (token) {
        return urlMethod(API_V1_RESOURCE, 'POST', 'add-tags', value, token)
            .then(resp => resp.json())
            .then(data => dispatchFCall(dispatch, data, f))
            .catch(error => dispatch(resourceRecordError(error)));
    }
}

export default function postTags(value, f) {
    console.log("postTags(" + value + ")")
    return dispatch => {
        return postTagsCall(dispatch, value, f);
    }
}
