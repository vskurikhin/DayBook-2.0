/*
 * This file was last modified at 2021.03.23 09:40 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NavigationBar.jsx
 * $Id$
 */

import {getProfileFetch, logoutUser, setCalendarDate} from '../../redux/actions'
import {isAnonymity, isAdmin} from '../../lib/userTool'
import {locales} from '../../config/locales';
import {setLanguage} from '../../redux/actions'

import React, {Component} from 'react'
import {Link} from 'react-router-dom'
import {Text, TranslationsProvider} from "@eo-locale/react";
import {Translator} from '@eo-locale/core';
import {compose} from 'redux'
import {connect} from 'react-redux'
import {map} from 'underscore'
import {withRouter} from 'react-router'

class NavigationBar extends Component {

    constructor(props) {
        super(props);
    }

    handleClick = event => {
        event.preventDefault()
        // Удаление token из sessionStorage
        window.sessionStorage.removeItem("token")
        // удаление из Redux хранилица
        this.props.logoutUser()
    }

    handleLanguage = event => {
        if ('ru' === this.props.locale.language) {
            this.props.setLanguage('en');
        }
        if ('en' === this.props.locale.language) {
            this.props.setLanguage('ru');
        }
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
        if (!isAdmin(this.props)) return null;
        return this.link(title, href, handleClick);
    }

    anonymously(title, href, handleClick) {
        if (!isAnonymity(this.props)) return null;
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

    render() {
        const translator = new Translator(this.props.locale.language, locales);
        this.sections = [
            {title: translator.getMessageById('Index'), href: '/', global: true},
            {title: translator.getMessageById('Calendar'), href: '/calendar', global: true},
            {title: translator.getMessageById('Create'), href: '/create', admin: true},
            {title: translator.getMessageById('Login'), href: '/login', anonymously: true},
            {title: translator.getMessageById('Log_Out'), href: '/', handleClick: true},
        ]
        return (
            <div className="my-navbar">
                <TranslationsProvider language={this.props.locale.language} locales={locales}>
                    {map(this.sections, ({title, href, admin, anonymously, global, handleClick}) => (
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
                    <a href="#" className="right" onClick={this.handleLanguage}><Text id='language'/></a>
                </TranslationsProvider>
            </div>
        )
    }
}

const mapStateToProps = state => ({
    user: state.currentUser,
    locale: state.language
})

const mapDispatchToProps = dispatch => ({
    getProfileFetch: () => dispatch(getProfileFetch()),
    logoutUser: () => dispatch(logoutUser()),
    setLanguage: value => dispatch(setLanguage(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(NavigationBar);
