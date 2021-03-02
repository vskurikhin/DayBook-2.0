/*
 * This file was last modified at 2021.03.02 23:08 by Victor N. Skurikhin.
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
                    <div className="fakeimg">Image</div>
                    <p>Some text about me in culpa qui officia deserunt mollit anim..</p>
                    <h3>More Text</h3>
                    <p>Lorem ipsum dolor sit ame.</p>
                </div>
            </TranslationsProvider>
        )
    }
}

const mapStateToProps = state => ({
    locale: state.language
})

export default connect(mapStateToProps)(Side);
