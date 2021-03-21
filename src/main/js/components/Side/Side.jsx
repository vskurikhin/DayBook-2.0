/*
 * This file was last modified at 2021.03.21 18:04 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Side.jsx
 * $Id$
 */

import {locales} from "../../config/locales";

import React, {Component} from 'react'
import {Text, TranslationsProvider} from "@eo-locale/react";
import {connect} from "react-redux";

class Side extends Component {

    render() {
        return (
            <TranslationsProvider language={this.props.locale.language} locales={locales}>
                <div className="my-side">
                    <h1><Text id='Index'/></h1>
                    <h2><Text id='About_Me'/></h2>
                    <h5><Text id='Photo_of_me'/>:</h5>
                    <img src="/raw-svg/my-cv-2021-03-21-228x358.png"/>
                    <p><Text id='My_name_is'/></p>
                    <h4><Text id='More_Text'/></h4>
                </div>
            </TranslationsProvider>
        )
    }
}

const mapStateToProps = state => ({
    locale: state.language
})

export default connect(mapStateToProps)(Side);
