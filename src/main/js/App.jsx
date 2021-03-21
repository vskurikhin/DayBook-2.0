/*
 * This file was last modified at 2021.03.21 13:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * App.jsx
 * $Id$
 */

import './App.scss';

import Calendar from './components/Calendar/Calendar';
import Create from "./components/Edit/Create";
import Edit from './components/Edit/Edit';
import CIndex from './components/Index/CIndex';
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
import 'primeicons/primeicons.css';
import 'primereact/resources/primereact.min.css';
import 'primeflex/primeflex.css';
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
          <Route history={history} path='/index' component={CIndex} />
          <Route history={history} path='/calendar' component={Calendar} />
          <Route history={history} path='/create' component={Create} />
          <Route history={history} path='/login' component={Login} />
          <Route history={history} path='/signup' component={Signup} />
          <Route history={history} path='/edit/:object/:id' component={Edit} />

          <Redirect from='/' to='/index'/>
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
