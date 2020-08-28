import React, { Component } from 'react'

import './Row.scss'

export default class Home extends Component {

  render () {
    return (
        <div className="row">
            <div className="side">
                <h1>Calendar</h1>
                <h2>About Me</h2>
                <h5>Photo of me:</h5>
                <div className="fakeimg">Image</div>
                <p>Some text about me in culpa qui officia deserunt mollit anim..</p>
                <h3>More Text</h3>
                <p>Lorem ipsum dolor sit ame.</p>
            </div>
            <div className="main">
            </div>
        </div>
    )
  }
}
