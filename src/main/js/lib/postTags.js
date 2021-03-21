/*
 * This file was last modified at 2021.03.21 13:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * postTags.js
 * $Id$
 */

import urlMethod from "./urlMethod";
import {API_V1_RESOURCE} from "../config/api";
import {resourceRecordError} from "../redux/resourceRecord";

function dispatchFCall(dispatch, data, f) {
    if (data.error) {
        throw(data.error);
    }
    dispatch(f(data.object));

    return data.object;
}

export default function postTags(dispatch, value, f) {
    const token = window.sessionStorage.token;
    if (token) {
        return urlMethod(API_V1_RESOURCE, 'POST', 'add-tags', value, token)
            .then(resp => resp.json())
            .then(data => dispatchFCall(dispatch, data, f))
            .catch(error => dispatch(resourceRecordError(error)));
    }
}
