/*
 * This file was last modified at 2021.02.04 22:49 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AdminRow.jsx
 * $Id$
 */

import React, {Component} from 'react'
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router";

import IFrame from '../IFrame/IFrame'
import AdminDataView from '../AdminDataView/AdminDataView'
import {loadCssListIframe1} from '../../lib/CssListIframe1'
import {setCalendarDate} from "../../redux/actions";
import {logDate} from "../../lib/formatDate";

export class AdminRow extends Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        loadCssListIframe1()
    }

    getCurrentUser() {
        return this.props.currentUser['currentUser'];
    }

    getCurrentDate() {
        return this.props.currentDate['currentDate'];
    }

    render() {
        const divStyle = {
            borderStyle: 'none',
            width: '100%',
            height: '100%'
        };
        console.log("HomeRow.render currentUser=" + this.getCurrentUser());
        logDate("HomeRow.render: currentDate=", this.getCurrentDate());

        return (
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
                <div className="my-main" id='main'>
                    <IFrame style={divStyle} name='iframe1' id='iframe1'>
                        <AdminDataView/>
                    </IFrame>
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
)(AdminRow);
