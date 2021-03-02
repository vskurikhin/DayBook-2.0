/*
 * This file was last modified at 2021.03.02 23:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * index.js
 * $Id$
 */

import currentDate from './currentDate';
import currentUser from './currentUser';
import language from './language';
import updatedRecord from './updatedRecord';

import {combineReducers} from 'redux';
import {routerReducer} from 'react-router-redux';

export default combineReducers({
  routing: routerReducer,
  currentDate: currentDate,
  currentUser,
  language,
  updatedRecord: updatedRecord,
});
