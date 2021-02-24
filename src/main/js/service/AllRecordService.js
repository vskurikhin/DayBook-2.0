/*
 * This file was last modified at 2021.02.24 18:51 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AllRecordService.js
 * $Id$
 */

import axios from 'axios';

import {getConfigHeadersAuthorization} from '../lib/axiosConfig';

const API_V1_RESOURCE_RECORDS = "/api/v1/resource/records";

export class AllRecordService {

    getCarsLazy(event, numberOfElements) {
        const config = getConfigHeadersAuthorization(localStorage.token);
        const first = event !== null ? event.first : 0;
        const page = event !== null ? event.page : 0;
        return axios
            .get(API_V1_RESOURCE_RECORDS + "?page=" + page + "&first=" + first + "&size=" + numberOfElements, config)
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