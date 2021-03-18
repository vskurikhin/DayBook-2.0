/*
 * This file was last modified at 2021.02.27 11:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AllRecordService.js
 * $Id$
 */

import axios from 'axios';

import {API_V1_RESOURCE_RECORDS, API_V1_RESOURCE_RECORDS_BY_DATE} from '../config/api';
import {getConfigHeadersAuthorization} from '../lib/axiosConfig';
import {ApiService} from "./ApiService";

export class AllRecordService extends ApiService {

    constructor(cancelTokenSource, date) {
        const url = date !== undefined ? API_V1_RESOURCE_RECORDS_BY_DATE : API_V1_RESOURCE_RECORDS;
        super(url, cancelTokenSource);
        this.date = date;
    }

    getRecordsLazy(event, numberOfElements, date) {
        const config = getConfigHeadersAuthorization(window.sessionStorage.token);
        const first = event !== null ? event.first : 0;
        const page = event !== null ? event.page : 0;
        const apiUrl = date !== undefined ? API_V1_RESOURCE_RECORDS_BY_DATE : API_V1_RESOURCE_RECORDS;
        const baseUrl = apiUrl + "?page=" + page + "&first=" + first + "&size=" + numberOfElements;
        const url = date !== undefined ? baseUrl + "&date=" + date : baseUrl;
        return axios
            .get(url, config)
            .then(function (response) {
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