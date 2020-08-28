import React, { Component } from 'react';

import {
  Route,
  Switch,
  Redirect,
  withRouter
} from "react-router-dom"

import './App.scss';

import Calendar from './components/Calendar/Calendar'
import Home from './components/Home/Home'

const iframe1 = document.getElementById('iframe1');

class App extends Component {

  render() {
    const { history } = this.props

    return (
      <div className="App">
        <Switch>
          <Route history={history} path='/home' component={Home} />
          <Route history={history} path='/calendar' component={Calendar} />
          <Redirect from='/' to='/home'/>
        </Switch>
      </div>
    );
  }
}

export default withRouter(App)