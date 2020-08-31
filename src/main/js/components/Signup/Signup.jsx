/*
 * This file was last modified at 2020.08.31 14:07 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Signup.jsx
 * $Id$
 */

import React, {Component} from 'react'
import {connect} from 'react-redux'
import {userPostFetch} from '../../redux/actions'
import Header from '../Header/Header'
import NavigationBar from '../NavigationBar/NavigationBar'

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
                            <h1>Sign Up For An Account</h1>

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

                            <label>E-mail</label>
                            <input
                                name='email'
                                placeholder='E-mail'
                                value={this.state.email}
                                onChange={this.handleChange}
                            /><br/>

                            <input type='submit'/>
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