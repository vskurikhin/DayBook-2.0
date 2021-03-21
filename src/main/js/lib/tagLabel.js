/*
 * This file was last modified at 2021.03.21 17:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * tagLabel.js
 * $Id$
 */

import {API_V1_RESOURCE} from "../config/api";
import {tagLabelError} from "../redux/tagLabel";
import urlMethod from "./urlMethod";

function dispatchFCall(dispatch, data, f) {
    console.log("dispatchFCall(" + data + ")")
    if (data.error) {
        throw(data.error);
    }
    dispatch(f(data.object));

    return data.object;
}

export function tagLabelCall(dispatch, value, f) {
    console.log("tagLabelCall(" + value + ")")
    const token = window.sessionStorage.token;
    if (token) {
        return urlMethod(API_V1_RESOURCE, 'POST', 'tag-label', value, token)
            .then(resp => resp.json())
            .then(data => dispatchFCall(dispatch, data, f))
            .catch(error => dispatch(tagLabelError(error)));
    }
}

export default function tagLabel(value, f) {
    console.log("tagLabel(" + value + ")")
    return dispatch => {
        return tagLabelCall(dispatch, value, f);
    }
}
