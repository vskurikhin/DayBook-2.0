import React, { Component } from 'react'

import './Calendar.scss'

import Header from '../Header/Header'
import NavigationBar from '../NavigationBar/NavigationBar'
import Row from '../Row/Row'

export default class Home extends Component {

  render () {
    return (
      <div>
        <Header />
        <NavigationBar />
        <Row />
        <div className="footer">
        </div>
      </div>
    )
  }
}
