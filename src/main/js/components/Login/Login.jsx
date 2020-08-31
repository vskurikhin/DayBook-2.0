/*
 * This file was last modified at 2020.08.31 14:07 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Login.jsx
 * $Id$
 */

import React, {Component} from 'react'
import {Redirect} from 'react-router';
import {connect} from 'react-redux'

import Header from '../Header/Header'
import NavigationBar from '../NavigationBar/NavigationBar'
import {userLoginFetch} from '../../redux/actions'

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
            return <Redirect to="/home" />
        }
        return (
            <div>
                <Header/>
                <NavigationBar/>
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
                        <form onSubmit={this.handleSubmit}>
                            <h1>Login</h1>

                            <label>Username</label>
                            <input
                                name='username'
                                placeholder='Username'
                                value={this.state.username}
                                onChange={this.handleChange}
                            /><br/>

                            <label>Password</label>
                            <input
                                type='password'
                                name='password'
                                placeholder='Password'
                                value={this.state.password}
                                onChange={this.handleChange}
                            /><br/>

                            <input type='submit'/>
                        </form>
                    </div>
                </div>
                <div className="footer">
                </div>
            </div>
        )
    }
}

const mapDispatchToProps = dispatch => ({
    userLoginFetch: userInfo => dispatch(userLoginFetch(userInfo))
})

export default connect(null, mapDispatchToProps)(Login);
