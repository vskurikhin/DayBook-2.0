/*
 * This file was last modified at 2021.03.21 17:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * tagLabel.js
 * $Id$
 */

import {
    TAG_LABEL_ERROR,
    TAG_LABEL_PENDING,
    TAG_LABEL_SUCCESS
} from '../redux/tagLabel';

const initialState = {
    pending: false,
    tags: {},
    error: null
}

export default function tagLabel(state = initialState, action) {
    switch(action.type) {
        case TAG_LABEL_PENDING:
            return {
                ...state,
                pending: true
            }
        case TAG_LABEL_SUCCESS:
            return {
                ...state,
                pending: false,
                tag: action.payload
            }
        case TAG_LABEL_ERROR:
            return {
                ...state,
                pending: false,
                error: action.error
            }
        default:
            return state;
    }
}

export const getTagLabel = state => state.tags;
export const getTagLabelPending = state => state.pending;
export const getTagLabelError = state => state.error;
