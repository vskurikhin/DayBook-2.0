/*
 * This file was last modified at 2021.02.02 21:06 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Row.jsx
 * $Id$
 */

import React, { Component } from 'react'

export default class Home extends Component {

  render () {
    return (
        <div className="my-row">
            <div className="my-side">
                <h1>Calendar</h1>
                <h2>About Me</h2>
                <h5>Photo of me:</h5>
                <div className="fakeimg">Image</div>
                <p>Some text about me in culpa qui officia deserunt mollit anim..</p>
                <h3>More Text</h3>
                <p>Lorem ipsum dolor sit ame.</p>
            </div>
            <div className="my-main">
            </div>
        </div>
    )
  }
}
