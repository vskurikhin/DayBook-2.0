/*
 * This file was last modified at 2020.08.30 09:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * index.jsx
 * $Id$
 */

import React from 'react';
import ReactDOM from 'react-dom';
import thunk from 'redux-thunk';
import { Provider } from 'react-redux';
import { Router, Route, hashHistory } from 'react-router';
import { composeWithDevTools } from 'redux-devtools-extension';
import { createBrowserHistory } from 'history';
import { createStore, applyMiddleware } from 'redux';
import { syncHistoryWithStore } from 'react-router-redux';

import './index.scss'
import reducer from './reducers';
import App from './App'

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
