/*
 * This file was last modified at 2021.02.27 11:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AllRecordService.js
 * $Id$
 */

import axios from 'axios';

import {API_V1_RESOURCE_RECORDS} from '../config/api';
import {getConfigHeadersAuthorization} from '../lib/axiosConfig';
import {ApiService} from "./ApiService";

export class AllRecordService extends ApiService {

    constructor(url, cancelTokenSource) {
        super(url, cancelTokenSource);
    }

    getRecordsLazy(event, numberOfElements) {
        const config = getConfigHeadersAuthorization(window.sessionStorage.token);
        const first = event !== null ? event.first : 0;
        const page = event !== null ? event.page : 0;
        return axios
            .get(API_V1_RESOURCE_RECORDS + "?page=" + page + "&first=" + first + "&size=" + numberOfElements, config)
            .then(function (response) {
                console.log('getCarsLazy(' + event + ',' + numberOfElements + ') data: ');
                console.log(response.data);
                const length = response.data.content.length;
                for (let i = length; i < numberOfElements; i++) {
                    response.data.content.push({});
                }
                return response;
            })
            .catch(function (error) {
                console.log(error);
            });
    }
}