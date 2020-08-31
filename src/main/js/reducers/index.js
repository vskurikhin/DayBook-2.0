import { combineReducers } from 'redux';
import { routerReducer } from 'react-router-redux';

import calendarDate from './calendarDate';
import currentUser from './currentUser';

export default combineReducers({
  routing: routerReducer,
  calendarDate,
  currentUser
});
