import React, { Component, useState, useEffect } from 'react'

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
      // console.log('iframe1' + iframe1);
      var head = iframe1.contentWindow.document.getElementsByTagName("head")[0];
      // console.log('head' + head);
      FILES.forEach(function(item, i, arr) {
          var cssLink = document.createElement("link", {href: item, rel: 'stylesheet', type: 'text/css'});
          cssLink.href = item;
          cssLink.rel = "stylesheet";
          cssLink.type = "text/css";
          // console.log('cssLink' + cssLink.outerHTML);
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
        <div className="row">
            <div className="side">
                <h1>Home</h1>
                <h2>About Me</h2>
                <h5>Photo of me:</h5>
                <div className="fakeimg">Image</div>
                <p>Some text about me in culpa qui officia deserunt mollit anim..</p>
                <h3>More Text</h3>
                <p>Lorem ipsum dolor sit ame.</p>
            </div>
            <div className="main" name='main'>
              <IFrame style={divStyle} name='iframe1' id='iframe1'>
                <RootDataViewLazy />
              </IFrame>
            </div>
        </div>
    )
  }
}
