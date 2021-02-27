/*
 * This file was last modified at 2021.02.27 11:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ApiService.js
 * $Id$
 */

import axios from 'axios';

import {getConfigHeadersAuthorization} from '../lib/axiosConfig';

export class ApiService {

    constructor(url, cancelTokenSource) {
        this.url = url;
        this.config = {
            ...getConfigHeadersAuthorization(window.sessionStorage.token),
            cancelToken: cancelTokenSource.token
        };
    }

    getAll(event, thenFunction) {
        return axios
            .get(this.url, this.config)
            .then(thenFunction)
            .catch(function (error) {
                console.log(error);
            });
    }

    getById(event, id, thenFunction) {
        return axios
            .get(this.url + '/' + id, this.config)
            .then(thenFunction)
            .catch(function (error) {
                console.log(error);
            });
    }

    getsPage(event, page, first, size, thenFunction) {
        return axios
            .get(this.url + + "?page=" + page + "&first=" + first + "&size=" + size, this.config)
            .then(thenFunction)
            .catch(function (error) {
                console.log(error);
            });
    }
}