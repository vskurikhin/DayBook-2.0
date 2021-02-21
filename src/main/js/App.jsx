/*
 * This file was last modified at 2021.02.21 16:52 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * App.jsx
 * $Id$
 */

import './App.scss'

import AdminCreate from './components/AdminCreate/AdminCreate'
import AdminEdit from './components/AdminEdit/AdminEdit'
import Calendar from './components/Calendar/Calendar'
import Home from './components/Home/Home'
import Login from './components/Login/Login'
import Signup from './components/Signup/Signup'
import {getProfileFetch, logoutUser} from './redux/actions'

import React, { Component } from 'react'
import {
  Route,
  Switch,
  Redirect,
  withRouter
} from "react-router"
import {compose} from 'redux'
import {connect} from 'react-redux'

class App extends Component {
  componentDidMount = () => {
    this.props.getProfileFetch()
  }

  render() {
    const { history } = this.props

    return (
      <div className="App">
        <Switch>
          <Route history={history} path='/home' component={Home} />
          <Route history={history} path='/calendar' component={Calendar} />
          <Route history={history} path='/login' component={Login} />
          <Route history={history} path='/signup' component={Signup} />
          <Route history={history} path='/create' component={AdminCreate} />
          <Route history={history} path='/edit' component={AdminEdit} />
          <Redirect from='/' to='/home'/>
        </Switch>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  currentUser: state.currentUser,
  currentDate: state.currentDate
})

const mapDispatchToProps = dispatch => ({
  getProfileFetch: () => dispatch(getProfileFetch()),
  logoutUser: () => dispatch(logoutUser())
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(App);
