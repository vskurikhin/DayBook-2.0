/*
 * This file was last modified at 2021.03.21 13:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordService.js
 * $Id$
 */

import {getConfigHeadersAuthorization} from '../lib/axiosConfig';
import {API_V1_RESOURCE_RECORD} from "../config/api";

import axios from 'axios';

class RecordService {

    getArticle(id, thenFunction, cancelTokenSource) {
        return this.getRecord(id, 'article', thenFunction, cancelTokenSource);
    }

    getNewsEntry(id, thenFunction, cancelTokenSource) {
        return this.getRecord(id, 'news-entry', thenFunction, cancelTokenSource);
    }

    getRecord(id, object, thenFunction, cancelTokenSource) {
        const config = {
            ...getConfigHeadersAuthorization(window.sessionStorage.token),
            cancelToken: cancelTokenSource.token
        };
        return axios.get(API_V1_RESOURCE_RECORD + '/' + object + '/' + id, config)
            .then(thenFunction)
            .catch(error => console.log(error));
    }
}

export const recordService = new RecordService();