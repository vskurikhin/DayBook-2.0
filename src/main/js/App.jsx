/*
 * This file was last modified at 2020.08.31 14:07 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * App.jsx
 * $Id$
 */

import React, { Component } from 'react'
import {
  Route,
  Switch,
  Redirect,
  withRouter
} from "react-router"
import {compose} from 'redux'
import {connect} from 'react-redux'

import './App.scss'
import Calendar from './components/Calendar/Calendar'
import Home from './components/Home/Home'
import Login from './components/Login/Login'
import Signup from './components/Signup/Signup'
import { getProfileFetch, logoutUser } from './redux/actions'

class App extends Component {
  componentDidMount = () => {
    this.props.getProfileFetch()
  }

  handleClick = event => {
    event.preventDefault()
    // Удаление token из localStorage
    localStorage.removeItem("token")
    // удаление из Redux хранилица
    this.props.logoutUser()
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
          <Redirect from='/' to='/home'/>
        </Switch>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  currentUser: state.currentUser
})

const mapDispatchToProps = dispatch => ({
  getProfileFetch: () => dispatch(getProfileFetch()),
  logoutUser: () => dispatch(logoutUser())
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(App);
