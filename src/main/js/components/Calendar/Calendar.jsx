/*
 * This file was last modified at 2021.02.02 21:06 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Calendar.jsx
 * $Id$
 */

import React, { Component } from 'react'

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
