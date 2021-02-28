/*
 * This file was last modified at 2021.02.28 23:25 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * index.js
 * $Id$
 */

import currentDate from './currentDate';
import currentUser from './currentUser';
import updatedRecord from './updatedRecord';

import {combineReducers} from 'redux';
import {routerReducer} from 'react-router-redux';

export default combineReducers({
  routing: routerReducer,
  currentDate: currentDate,
  currentUser,
  updatedRecord: updatedRecord
});
