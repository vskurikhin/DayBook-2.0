/*
 * This file was last modified at 2020.08.30 09:14 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Signup.jsx
 * $Id$
 */

import React, {Component} from 'react';
import {connect} from 'react-redux';
import {userPostFetch} from '../../redux/actions';

class Signup extends Component {
    state = {
        username: "",
        password: "",
        avatar: "",
        bio: ""
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

                <label>Avatar</label>
                <input
                    name='avatar'
                    placeholder='Avatar (URL)'
                    value={this.state.avatar}
                    onChange={this.handleChange}
                /><br/>

                <label>Bio</label>
                <textarea
                    name='bio'
                    placeholder='Bio'
                    value={this.state.bio}
                    onChange={this.handleChange}
                /><br/>

                <input type='submit'/>
            </form>
        )
    }
}

const mapDispatchToProps = dispatch => ({
    userPostFetch: userInfo => dispatch(userPostFetch(userInfo))
})

export default connect(null, mapDispatchToProps)(Signup);