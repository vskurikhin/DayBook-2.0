/*
 * This file was last modified at 2021.02.24 00:07 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * index.jsx
 * $Id$
 */

import './index.scss'
import App from './App'
import reducer from './reducers';

import React from 'react';
import ReactDOM from 'react-dom';
import thunk from 'redux-thunk';
import {Provider} from 'react-redux';
import {Router} from 'react-router';
import {composeWithDevTools} from 'redux-devtools-extension';
import {createBrowserHistory} from 'history';
import {createStore, applyMiddleware} from 'redux';
import {syncHistoryWithStore} from 'react-router-redux';

const store = createStore(reducer, composeWithDevTools(applyMiddleware(thunk)));
const history = syncHistoryWithStore(createBrowserHistory(), store);

ReactDOM.render((
    <Provider store={store}>
        <Router history={history}>
            <App/>
        </Router>
    </Provider>
    ), document.getElementById('root')
);
