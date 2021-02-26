/*
 * This file was last modified at 2021.02.27 00:06 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * App.jsx
 * $Id$
 */

import './App.scss';

import Calendar from './components/Calendar/Calendar';
import Create from "./components/Create/Create";
import Edit from './components/Edit/Edit';
import Home from './components/Home/Home';
import Login from './components/Login/Login';
import Signup from './components/Signup/Signup'
import {getProfileFetch, logoutUser} from './redux/actions';

import React, { Component } from 'react';
import {
  Route,
  Switch,
  Redirect,
  withRouter
} from "react-router";
import {compose} from 'redux';
import {connect} from 'react-redux';

class App extends Component {
  componentDidMount = () => {
    this.props.getProfileFetch()
  }

  render() {
    const {history} = this.props

    return (
      <div className="App">
        <Switch>
          <Route history={history} path='/home' component={Home} />
          <Route history={history} path='/calendar' component={Calendar} />
          <Route history={history} path='/login' component={Login} />
          <Route history={history} path='/signup' component={Signup} />
          <Route history={history} path='/create' component={Create} />
          <Route history={history} path='/edit/:object/:id' component={Edit} />

          <Redirect from='/' to='/home'/>
        </Switch>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  user: state.currentUser,
  date: state.currentDate
})

const mapDispatchToProps = dispatch => ({
  getProfileFetch: () => dispatch(getProfileFetch()),
  logoutUser: () => dispatch(logoutUser())
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(App);
