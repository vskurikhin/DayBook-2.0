/*
 * This file was last modified at 2021.02.21 20:37 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AdminCreate.jsx
 * $Id$
 */

import AdminCreateRow from "../AdminCreateRow/AdminCreateRow";
import Header from "../Header/Header";
import NavigationBar from "../NavigationBar/NavigationBar";
import {setCalendarDate} from "../../redux/actions";

import React, { Component } from 'react';
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router";

class AdminCreate extends Component {

    render () {
        return (
            <div>
                <Header />
                <NavigationBar />
                <AdminCreateRow />
                <div className="my-footer">
                </div>
            </div>
        )
    }
}

const mapStateToProps = state => ({
    currentUser: state.currentUser,
    currentDate: state.currentDate
})

const mapDispatchToProps = dispatch => ({
    handleCalendarDate: value => dispatch(setCalendarDate(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(AdminCreate);
