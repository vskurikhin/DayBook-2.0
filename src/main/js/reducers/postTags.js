/*
 * This file was last modified at 2021.03.21 17:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * postTags.js
 * $Id$
 */

import {
    POST_TAGS_ERROR,
    POST_TAGS_PENDING,
    POST_TAGS_SUCCESS
} from '../redux/postTags';

const initialState = {
    pending: false,
    tags: {},
    error: null
}

export default function postTags(state = initialState, action) {
    switch(action.type) {
        case POST_TAGS_PENDING:
            return {
                ...state,
                pending: true
            }
        case POST_TAGS_SUCCESS:
            return {
                ...state,
                pending: false,
                tags: action.payload
            }
        case POST_TAGS_ERROR:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        default:
            return state;
    }
}

export const getPostTags = state => state.tags;
export const getPostTagsPending = state => state.pending;
export const getPostTagsError = state => state.error;
