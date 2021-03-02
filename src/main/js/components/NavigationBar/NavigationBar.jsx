/*
 * This file was last modified at 2021.03.02 17:18 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NavigationBar.jsx
 * $Id$
 */

import {getProfileFetch, logoutUser} from '../../redux/actions'
import {isAnonymity, isAdmin} from '../../lib/userTool'

import React, {Component} from 'react'
import {Link} from 'react-router-dom'
import {compose} from 'redux'
import {connect} from 'react-redux'
import {map} from 'underscore'
import {withRouter} from 'react-router'

const SECTIONS = [
    {title: 'Index', href: '/', global: true},
    {title: 'Calendar', href: '/calendar', global: true},
    {title: 'Create', href: '/create', admin: true},
    {title: 'Login', href: '/login', anonymously: true},
    {title: 'Signup', href: '/signup', anonymously: true},
    {title: 'Log Out', href: '/', handleClick: true},
]

class NavigationBar extends Component {

    handleClick = event => {
        event.preventDefault()
        // Удаление token из sessionStorage
        window.sessionStorage.removeItem("token")
        // удаление из Redux хранилица
        this.props.logoutUser()
    }

    link(title, href, handleClick) {
        return (
            <Link className='SectionNavigation-Item Section' to={href} key={title}
                  onClick={handleClick ? this.handleClick : null}>
                <span className='Section-Title'>{title}</span>
            </Link>
        );
    }

    admin(title, href, handleClick) {
        if ( ! isAdmin(this.props)) return null;
        return this.link(title, href, handleClick);
    }

    anonymously(title, href,handleClick) {
        if ( ! isAnonymity(this.props)) return null;
        return this.link(title, href, handleClick);
    }

    global(title, href, handleClick) {
        return this.link(title, href, handleClick);
    }

    other(title, href, handleClick) {
        if (isAnonymity(this.props)) return null;
        return this.link(title, href, handleClick);
    }

    save = () => {
        this.toast.current.show({severity: 'success', summary: 'Success', detail: 'Data Saved'});
    }

    add() {
        if ( ! isAdmin(this.props)) return null;
        return (
            <SplitButton label="Save" icon="pi pi-plus" onClick={this.save} model={items}/>
        )
    }

    render() {
        return (
            <div className="my-navbar">
                {map(SECTIONS, ({title, href, admin, anonymously, global, handleClick}) => (
                    // с помощью компонента Link будет осуществляться
                    // навигация по разделам приложения
                    admin
                        ? this.admin(title, href, handleClick)
                        : anonymously
                            ? this.anonymously(title, href, handleClick)
                            : global
                                ? this.global(title, href, handleClick)
                                : this.other(title, href, handleClick)
                ))}
                <a href="#" className="right">Russian</a>
            </div>
        )
    }
}

const mapStateToProps = state => ({
    user: state.currentUser
})

const mapDispatchToProps = dispatch => ({
    getProfileFetch: () => dispatch(getProfileFetch()),
    logoutUser: () => dispatch(logoutUser())
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(NavigationBar);
