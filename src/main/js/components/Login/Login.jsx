/*
 * This file was last modified at 2021.02.22 17:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Login.jsx
 * $Id$
 */

import Header from '../Header/Header';
import NavigationBar from '../NavigationBar/NavigationBar';
import {userLoginFetch} from '../../redux/actions';

import React, {Component} from 'react';
import {Redirect} from 'react-router';
import {connect} from 'react-redux';
import {InputText} from "primereact/inputtext";
import {Button} from "primereact/button";

class Login extends Component {
    state = {
        username: "",
        password: "",
        redirectToReferrer: false
    }

    handleChange = event => {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    handleSubmit = event => {
        event.preventDefault()
        this.props.userLoginFetch(this.state)
        this.setState({redirectToReferrer: true})
    }

    render() {
        const redirectToReferrer = this.state.redirectToReferrer;
        if (redirectToReferrer === true) {
            return <Redirect to="/home"/>
        }
        return (
            <div>
                <Header/>
                <NavigationBar/>
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
                        <div className="dataview-demo">
                            <form onSubmit={this.handleSubmit}>
                                <div className="my-divTable">
                                    <div className="my-divTableBody">
                                        <div className="my-divTableRow">
                                            <div className="my-divTableCellLeft">&nbsp;</div>
                                            <div className="my-divTableCell">
                                                <h1>Login</h1>
                                                <br/>
                                                <span className="p-float-label">
                                                        <InputText
                                                            className="my-p-inputtext"
                                                            id="Username"
                                                            name='username'
                                                            onChange={this.handleChange}
                                                            value={this.state.username}
                                                        />
                                                        <label htmlFor="Username"><b>Username:</b></label>
                                                    </span>
                                            </div>
                                            <div className="my-divTableCellRight">&nbsp;</div>
                                        </div>
                                        <div className="my-divTableRow">
                                            <div className="my-divTableCellLeft">&nbsp;</div>
                                            <div className="my-divTableCell">
                                                <label className="my-label"><b>Password:</b></label><br/>
                                                <input
                                                    className="my-p-inputtext"
                                                    name='password'
                                                    onChange={this.handleChange}
                                                    placeholder='Password'
                                                    type='password'
                                                    value={this.state.password}
                                                /><br/>
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
                </div>
                <div className="my-footer">
                </div>
            </div>
        )
    }
}

const mapDispatchToProps = dispatch => ({
    userLoginFetch: userInfo => dispatch(userLoginFetch(userInfo))
})

export default connect(null, mapDispatchToProps)(Login);
