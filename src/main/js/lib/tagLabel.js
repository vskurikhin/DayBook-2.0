/*
 * This file was last modified at 2021.03.23 09:40 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * tagLabel.js
 * $Id$
 */

import {API_V1_RESOURCE} from "../config/api";
import {tagLabelError} from "../redux/tagLabel";
import urlMethod from "./urlMethod";

function dispatchFCall(dispatch, data, f) {
    if (data.error) {
        throw(data.error);
    }
    dispatch(f(data.object));

    return data.object;
}

export function tagLabelCall(dispatch, value, f) {
    const token = window.sessionStorage.token;
    if (token) {
        return urlMethod(API_V1_RESOURCE, 'POST', 'tag-label', value, token)
            .then(resp => resp.json())
            .then(data => dispatchFCall(dispatch, data, f))
            .catch(error => dispatch(tagLabelError(error)));
    }
}

export default function tagLabel(value, f) {
    return dispatch => {
        return tagLabelCall(dispatch, value, f);
    }
}
