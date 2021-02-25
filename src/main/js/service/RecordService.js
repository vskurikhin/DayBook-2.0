/*
 * This file was last modified at 2021.02.25 22:27 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordService.js
 * $Id$
 */

import axios from 'axios';

import {getConfigHeadersAuthorization} from '../lib/axiosConfig';

const API_V1_RESOURCE_RECORD_FETCH = "/api/v1/resource/record/";

class RecordService {

    getArticle(id, thenFunction, cancelTokenSource) {
        this.getRecord(id, 'article', thenFunction, cancelTokenSource);
    }

    getNewsEntry(id, thenFunction, cancelTokenSource) {
        this.getRecord(id, 'news-entry', thenFunction, cancelTokenSource);
    }

    getNewsLinks(id, thenFunction, cancelTokenSource) {
        this.getRecord(id, 'news-links', thenFunction, cancelTokenSource);
    }

    getRecord(id, object, thenFunction, cancelTokenSource) {
        const config = {
            ...getConfigHeadersAuthorization(localStorage.token),
            cancelToken: cancelTokenSource.token
        };
        axios.get(API_V1_RESOURCE_RECORD_FETCH + object + '/' + id, config)
            .then(thenFunction)
            .catch(function (error) {
                console.log(error);
            });
    }
}

export const recordService = new RecordService();