/*
 * This file was last modified at 2020.08.27 09:52 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * index.jsx
 * $Id$
 */

import React from 'react'
import ReactDOM from 'react-dom'

import { Router } from "react-router-dom"
import { createBrowserHistory } from 'history'

import './index.scss'

import App from './App'

// создаём кастомную историю
const history = createBrowserHistory()

ReactDOM.render((
    <Router history={history}>
      <App/>
    </Router>
  ), document.getElementById('root')
);
