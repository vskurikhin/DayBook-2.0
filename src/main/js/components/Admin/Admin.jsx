/*
 * This file was last modified at 2021.02.04 22:49 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Admin.jsx
 * $Id$
 */

import React, { Component } from 'react'

import Header from '../Header/Header'
import NavigationBar from '../NavigationBar/NavigationBar'
import AdminRow from '../AdminRow/AdminRow'

export default class Admin extends Component {

  render () {
    return (
      <div>
        <Header />
        <NavigationBar />
        <AdminRow />
        <div className="my-footer">
        </div>
      </div>
    )
  }
}
