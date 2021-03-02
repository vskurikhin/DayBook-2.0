/*
 * This file was last modified at 2021.03.02 23:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Signup.jsx
 * $Id$
 */

import Header from '../Header/Header'
import NavigationBar from '../NavigationBar/NavigationBar'
import {locales} from "../../config/locales";
import {userLoginFetch, userPostFetch} from '../../redux/actions'

import React, {Component} from 'react'
import {Button} from "primereact/button";
import {InputText} from "primereact/inputtext";
import {Text, TranslationsProvider} from "@eo-locale/react";
import {Translator} from '@eo-locale/core';
import {connect} from 'react-redux'
import {compose} from "redux";
import {withRouter} from "react-router";
import Side from "../Side/Side";

class Signup extends Component {
    state = {
        username: "",
        password: "",
        email: ""
    }

    handleChange = event => {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    handleSubmit = event => {
        event.preventDefault()
        this.props.userPostFetch(this.state)
    }

    render() {
        const translator = new Translator(this.props.locale.language, locales);
        return (
            <div>
                <TranslationsProvider language={this.props.locale.language} locales={locales}>
                    <Header/>
                    <NavigationBar/>
                    <div className="my-row">
                        <Side/>
                        <div className="my-main" name='main'>
                            <form onSubmit={this.handleSubmit}>
                                <div className="my-divTable">
                                    <div className="my-divTableBody">
                                        <div className="my-divTableRow">
                                            <div className="my-divTableCellLeft">&nbsp;</div>
                                            <div className="my-divTableCell">
                                                <h1>Sign-up</h1>
                                                <br/>
                                                <span className="p-float-label">
                                                        <InputText
                                                            className="my-p-inputtext"
                                                            id="Username"
                                                            name='username'
                                                            onChange={this.handleChange}
                                                            value={this.state.username}
                                                        />
                                                        <label htmlFor="Username"><b><Text id='Username'/>:</b></label>
                                                    </span>
                                            </div>
                                            <div className="my-divTableCellRight">&nbsp;</div>
                                        </div>
                                        <div className="my-divTableRow">
                                            <div className="my-divTableCellLeft">&nbsp;</div>
                                            <div className="my-divTableCell">
                                                <label className="my-label"><b><Text id='Password'/>:</b></label><br/>
                                                <input
                                                    className="my-p-inputtext"
                                                    name='password'
                                                    onChange={this.handleChange}
                                                    placeholder={translator.getMessageById('Password')}
                                                    type='password'
                                                    value={this.state.password}
                                                /><br/>
                                            </div>
                                            <div className="my-divTableCellRight">&nbsp;</div>
                                        </div>
                                        <div className="my-divTableRow">
                                            <div className="my-divTableCellLeft">&nbsp;</div>
                                            <div className="my-divTableCell">
                                                <br/>
                                                <span className="p-float-label">
                                                        <InputText
                                                            className="my-p-inputtext"
                                                            id="Email"
                                                            name='email'
                                                            onChange={this.handleChange}
                                                            value={this.state.email}
                                                        />
                                                        <label htmlFor="Email"><b>E-mail:</b></label>
                                                    </span>
                                            </div>
                                            <div className="my-divTableCellRight">&nbsp;</div>
                                        </div>
                                        <div className="my-divTableRow">
                                            <div className="my-divTableCellLeft">&nbsp;</div>
                                            <div className="my-divTableCell">
                                                <Button
                                                    className="my-p-button"
                                                    icon="pi pi-check"
                                                    iconPos="right"
                                                    label="Submit"
                                                />
                                            </div>
                                            <div className="my-divTableCellRight">&nbsp;</div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div className="my-footer">
                    </div>
                </TranslationsProvider>
            </div>
        )
    }
}

const mapStateToProps = state => ({
    locale: state.language
})

const mapDispatchToProps = dispatch => ({
    userLoginFetch: userInfo => dispatch(userLoginFetch(userInfo))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(Signup);
