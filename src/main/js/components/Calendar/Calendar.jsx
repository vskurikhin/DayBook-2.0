/*
 * This file was last modified at 2021.02.21 16:52 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Calendar.jsx
 * $Id$
 */

import Header from '../Header/Header'
import NavigationBar from '../NavigationBar/NavigationBar'
import CalendarRow from '../CalendarRow/CalendarRow'

import React, { Component } from 'react'

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
