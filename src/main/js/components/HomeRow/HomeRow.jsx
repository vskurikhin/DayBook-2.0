/*
 * This file was last modified at 2020.08.31 13:23 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * HomeRow.jsx
 * $Id$
 */

import React, { Component } from 'react'

import IFrame from '../IFrame/IFrame'
import RootDataViewLazy from '../RootDataViewLazy/RootDataViewLazy'

import './HomeRow.scss'

const FILES = [
  '/css/theme.css',
  '/css/primeicons.css',
  '/css/main.362f8fd8.chunk.css'
]

export default class HomeRow extends Component {

  constructor(props) {
    super(props);
    this.state = {
      count: 0
    };
  }

  componentDidMount() {
      var iframe1 = document.getElementById('iframe1'); // getElementsByTagName("iframe")[0].contentWindow;
      var head = iframe1.contentWindow.document.getElementsByTagName("head")[0];
      FILES.forEach(function(item, i, arr) {
          var cssLink = document.createElement("link", {href: item, rel: 'stylesheet', type: 'text/css'});
          cssLink.href = item;
          cssLink.rel = "stylesheet";
          cssLink.type = "text/css";
          head.appendChild(cssLink);
      });
  }

  render() {
    const divStyle = {
      borderStyle: 'none',
      width: '100%',
      height: '100%'
    };

    return (
        <div className="my-row">
            <div className="my-side">
                <h1>Home</h1>
                <h2>About Me</h2>
                <h5>Photo of me:</h5>
                <div className="fakeimg">Image</div>
                <p>Some text about me in culpa qui officia deserunt mollit anim..</p>
                <h3>More Text</h3>
                <p>Lorem ipsum dolor sit ame.</p>
            </div>
            <div className="my-main" name='main'>
              <IFrame style={divStyle} name='iframe1' id='iframe1'>
                <RootDataViewLazy />
              </IFrame>
            </div>
        </div>
    )
  }
}
