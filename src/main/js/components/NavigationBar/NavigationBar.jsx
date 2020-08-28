import React, { Component } from 'react'
import { map } from 'underscore'
import { Link } from "react-router-dom"

import './NavigationBar.scss'

const SECTIONS = [
  { title: 'Home', href: '/' },
  { title: 'Calendar', href: '/calendar' },
  { title: '|', href: '/admin' },
]

export default class Header extends Component {

  render () {
    return (
      <div className="navbar">
          {map(SECTIONS, ({ title, href }) => (
            // с помощью компонента Link будет осуществляться
            // навигация по разделам приложения
            <Link className='SectionNavigation-Item Section' to={href}>
              <span className='Section-Title'>{title}</span>
            </Link>
          ))}
          <a href = "#" className = "right">Russian</a>
      </div>
    )
  }
}
