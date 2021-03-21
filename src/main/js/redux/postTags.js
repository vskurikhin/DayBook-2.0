/*
 * This file was last modified at 2021.03.21 17:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * postTags.js
 * $Id$
 */

export const POST_TAGS_PENDING = 'POST_TAGS_PENDING';
export const POST_TAGS_SUCCESS = 'POST_TAGS_SUCCESS';
export const POST_TAGS_ERROR = 'POST_TAGS_ERROR';

export function postTagsPending() {
    return {
        type: POST_TAGS_PENDING
    }
}

export function postTagsSuccess(value) {
    return {
        type: POST_TAGS_SUCCESS,
        tags: value
    }
}

export function postTagsError(error) {
    return {
        type: POST_TAGS_ERROR,
        error: error
    }
}
