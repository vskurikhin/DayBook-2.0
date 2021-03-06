/*
 * This file was last modified at 2021.03.02 23:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Header.jsx
 * $Id$
 */

import {locales} from "../../config/locales";

import React, {Component} from 'react'
import {Text, TranslationsProvider} from "@eo-locale/react";
import {connect} from "react-redux";

class Header extends Component {

    render() {
        return (
            <TranslationsProvider language={this.props.locale.language} locales={locales}>
                <div className='my-header'>
                    <h1><Text id='SVN_DayBook_Website'/></h1>
                </div>
            </TranslationsProvider>
        )
    }
}

const mapStateToProps = state => ({
    locale: state.language
})

export default connect(mapStateToProps)(Header);
