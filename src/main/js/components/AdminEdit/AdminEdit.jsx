/*
 * This file was last modified at 2021.02.24 00:07 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AdminEdit.jsx
 * $Id$
 */

import AdminEditRow from "../AdminEditRow/AdminEditRow";
import Header from "../Header/Header";
import NavigationBar from "../NavigationBar/NavigationBar";
import {setCalendarDate} from "../../redux/actions";

import React, {Component} from 'react';
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router";

class AdminEdit extends Component {

    render () {
        return (
            <div>
                <Header />
                <NavigationBar />
                <AdminEditRow />
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
)(AdminEdit);
