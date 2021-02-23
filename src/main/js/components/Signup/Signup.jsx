/*
 * This file was last modified at 2021.02.22 17:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Signup.jsx
 * $Id$
 */

import Header from '../Header/Header'
import NavigationBar from '../NavigationBar/NavigationBar'
import {userPostFetch} from '../../redux/actions'

import React, {Component} from 'react'
import {Button} from "primereact/button";
import {InputText} from "primereact/inputtext";
import {connect} from 'react-redux'

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
            </div>
        )
    }
}

const mapDispatchToProps = dispatch => ({
    userPostFetch: userInfo => dispatch(userPostFetch(userInfo))
})

export default connect(null, mapDispatchToProps)(Signup);