/*
 * This file was last modified at 2021.03.02 17:18 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CIndex.jsx
 * $Id$
 */

import Header from '../Header/Header'
import NavigationBar from '../NavigationBar/NavigationBar'
import HomeRow from './IndexRow/IndexRow'

import React, { Component } from 'react'

export default class CIndex extends Component {

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
