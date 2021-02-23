/*
 * This file was last modified at 2021.02.23 11:21 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * store.js
 * $Id$
 */

import booksReducer from '../reducers/books';
import filtersReducer from '../reducers/filters';

import {createStore, combineReducers} from "redux";

const demoState = {
    books: [
        {
            id: '123abcdefghiklmn',
            title: 'Origin',
            description: 'Origin thrusts Robert Langdon into the dangerous intersection of humankind’s two most enduring questions.',
            author: 'Dan Brown',
            published: 2017
        }
    ],
    filters: {
        text: 'ori',
        sortBy: 'published', // published or title
        startYear: undefined,
        endYear: undefined
    }
};

export default () => {
    return createStore(
        combineReducers({
            books: booksReducer,
            filters: filtersReducer
        }
    ));
};