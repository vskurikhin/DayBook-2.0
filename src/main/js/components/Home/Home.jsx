/*
 * This file was last modified at 2021.02.02 19:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Home.jsx
 * $Id$
 */

import React, { Component } from 'react'

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
