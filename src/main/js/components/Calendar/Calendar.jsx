import React, { Component } from 'react'

import './Calendar.scss'
import Header from '../Header/Header'
import NavigationBar from '../NavigationBar/NavigationBar'
import CalendarRow from '../CalendarRow/CalendarRow'

export default class Calendar extends Component {

  render () {
    return (
      <div>
        <Header />
        <NavigationBar />
        <CalendarRow />
        <div className="my-footer">
        </div>
      </div>
    )
  }
}
