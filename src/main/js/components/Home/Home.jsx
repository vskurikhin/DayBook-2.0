import React, { Component } from 'react'

import './Home.scss'

import Header from '../Header/Header'
import NavigationBar from '../NavigationBar/NavigationBar'
import HomeRow from '../HomeRow/HomeRow'

export default class Home extends Component {

  render () {
    return (
      <div>
        <Header />
        <NavigationBar />
        <HomeRow />
        <div className="my-footer">
        </div>
      </div>
    )
  }
}
