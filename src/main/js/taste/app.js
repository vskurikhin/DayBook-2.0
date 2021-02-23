/*
 * This file was last modified at 2021.02.23 11:02 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * app.js
 * $Id$
 */

import React from 'react';
import ReactDOM from 'react-dom';
import AppRouter from './routers/AppRouter';
import getAppStore from './store/store';
import { addBook } from './actions/books';
import { filterText, startYear, endYear, sortBy, clear } from './actions/filters';
import getVisibleBooks from './selectors/books';
import './styles/styles.scss';

import { Provider } from 'react-redux';

const store = getAppStore();

store.dispatch(addBook({
    title: 'Origin',
    description: 'Origin thrusts Robert Langdon into the dangerous intersection of humankind’s two most enduring questions.',
    author: 'Dan Brown',
    published: 2017
}));

store.dispatch(addBook({
    title: 'Harry Potter and the Goblet of Fire',
    description: 'A young wizard finds himself competing in a hazardous tournament between rival schools of magic, but he is distracted by recurring nightmares.',
    author: 'J. K. Rowling',
    published: 2000
}));

store.dispatch(addBook({
    title: 'Harry Potter and the Deathly Hallows',
    description: 'The seventh and final novel of the Harry Potter series.',
    author: 'J. K. Rowling',
    published: 2007
}));

store.dispatch(addBook({
    title: 'The 100-Year-Old Man Who Climbed Out the Window and Disappeared',
    author: 'Jonas Jonasson',
    published: 2009
}));

const template = (
    <Provider store={store}>
        <AppRouter />
    </Provider>
);

ReactDOM.render(template, document.getElementById('app'));
