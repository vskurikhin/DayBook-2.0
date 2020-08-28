/*
 * This file was last modified at 2020.08.27 09:52 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CarService.js
 * $Id$
 */

import axios from 'axios';

export class CarService {

    getCarsFirstPage(page, first, rows) {
        return axios
            .get("/api/v1/pages?page=" + page + "&first=" + first + "&rows=" + rows)
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
            .get("/api/v1/pages?page=" + event.page + "&first=" + event.first + "&rows=" + event.rows)
            .then(function(response) {
                console.log(response);
                return response;
            })
            .catch(function(error) {
                console.log(error);
            });
    }
}