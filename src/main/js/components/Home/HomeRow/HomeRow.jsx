/*
 * This file was last modified at 2021.02.27 00:06 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * HomeRow.jsx
 * $Id$
 */

import IFrame from '../../IFrame/IFrame'
import RootDataViewLazy from '../../RootDataViewLazy/RootDataViewLazy'
import {loadCssListIframe1} from '../../../lib/CssListIframe1'
import {setCalendarDate} from "../../../redux/actions";

import React, {Component} from 'react'
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router";

export class HomeRow extends Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        loadCssListIframe1()
    }

    render() {
        const divStyle = {
            borderStyle: 'none',
            width: '100%',
            height: '100%'
        };

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
                        <RootDataViewLazy/>
                    </IFrame>
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
)(HomeRow);
