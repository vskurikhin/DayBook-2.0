/*
 * This file was last modified at 2021.02.22 14:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AllRecordService.js
 * $Id$
 */

import axios from 'axios';

import {getConfigHeadersAuthorization} from '../lib/axiosConfig';

export class AllRecordService {

    getCarsFirstPage(page, first, rows) {
        const config = getConfigHeadersAuthorization(localStorage.token)
        return axios
            .get("/api/v1/resource/records?page=" + page + "&first=" + first + "&size=" + rows, config)
            .then(function(response) {
                console.log(response);
                return response;
            })
            .catch(function(error) {
                console.log(error);
            });
    }

    getCarsLazy(event, rows) {
        console.log("Table lazy event", event);
        const config = getConfigHeadersAuthorization(localStorage.token)
        var filterJsonString = JSON.stringify(event);
        console.log("Filter", filterJsonString);
        return axios
            .get("/api/v1/resource/records?page=" + event.page + "&first=" + event.first + "&size=" + rows, config)
            .then(function(response) {
                console.log(response);
                const length = response.data.content.length;
                const numberOfElements = response.data['numberOfElements'] === null ? 3 : response.data['numberOfElements'];
                for (let i = length; i < numberOfElements; i++) {
                    response.data.content.push({});
                }
                console.log("fixed: " + JSON.stringify(response));
                return response;
            })
            .catch(function(error) {
                console.log(error);
            });
    }
}