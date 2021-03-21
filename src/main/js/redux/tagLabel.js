/*
 * This file was last modified at 2021.03.21 17:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * tagLabel.js
 * $Id$
 */

export const TAG_LABEL_PENDING = 'TAG_LABEL_PENDING';
export const TAG_LABEL_SUCCESS = 'TAG_LABEL_SUCCESS';
export const TAG_LABEL_ERROR = 'TAG_LABEL_ERROR';

export function tagLabelPending() {
    return {
        type: TAG_LABEL_PENDING
    }
}

export function tagLabelSuccess(value) {
    return {
        type: TAG_LABEL_SUCCESS,
        tag: value
    }
}

export function tagLabelError(error) {
    return {
        type: TAG_LABEL_ERROR,
        error: error
    }
}
