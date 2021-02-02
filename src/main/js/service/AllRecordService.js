/*
 * This file was last modified at 2021.02.02 19:29 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AllRecordService.js
 * $Id$
 */

import axios from 'axios';

export class AllRecordService {

    getCarsFirstPage(page, first, rows) {
        return axios
            .get("/api/v1/resource/records?page=" + page + "&first=" + first + "&size=" + rows)
            .then(function(response) {
                console.log(response);
                return response;
            })
            .catch(function(error) {
                console.log(error);
            });
    }

    getCarsLazy(event) {
        console.log("Table lazy event", event);
        var filterJsonString = JSON.stringify(event);
        console.log("Filter", filterJsonString);
        return axios
            .get("/api/v1/resource/records?page=" + event.page + "&first=" + event.first + "&size=" + event.rows)
            .then(function(response) {
                console.log(response);
                return response;
            })
            .catch(function(error) {
                console.log(error);
            });
    }
}