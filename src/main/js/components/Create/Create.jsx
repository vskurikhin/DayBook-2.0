/*
 * This file was last modified at 2021.02.27 00:06 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Create.jsx
 * $Id$
 */

import AdminCreateRow from "./CreateRow/CreateRow";
import Header from "../Header/Header";
import NavigationBar from "../NavigationBar/NavigationBar";
import {setCalendarDate} from "../../redux/actions";

import React, { Component } from 'react';
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router";

class Create extends Component {

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
    user: state.currentUser,
    date: state.currentDate
})

const mapDispatchToProps = dispatch => ({
    handleCalendarDate: value => dispatch(setCalendarDate(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(Create);
