/*
 * This file was last modified at 2021.02.24 00:07 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordService.js
 * $Id$
 */

import axios from 'axios';

import {getConfigHeadersAuthorization} from '../lib/axiosConfig';

const API_V1_RESOURCE_RECORD_FETCH = "/api/v1/resource/record/news-entry";

class RecordService {

    getRecord(id, thenFunction, cancelTokenSource) {
        const config = {
            ...getConfigHeadersAuthorization(localStorage.token),
            cancelToken: cancelTokenSource.token
        };
        axios.get(API_V1_RESOURCE_RECORD_FETCH + '/' + id, config)
             .then(thenFunction)
             .catch(function (error) {
                 console.log(error);
             });
    }
}

export const recordService = new RecordService();