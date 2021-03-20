/*
 * This file was last modified at 2021.03.20 15:52 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * index.js
 * $Id$
 */

import currentDate from './currentDate';
import currentPage from './currentPage';
import currentUser from './currentUser';
import language from './language';
import resourceRecord from "./resourceRecord";

import {combineReducers} from 'redux';
import {routerReducer} from 'react-router-redux';

export default combineReducers({
  routing: routerReducer,
  currentDate: currentDate,
  currentPage,
  currentUser,
  language,
  resourceRecord,
});
