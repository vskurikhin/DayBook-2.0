/*
 * This file was last modified at 2021.03.02 23:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * IndexRow.jsx
 * $Id$
 */

import IFrame from '../../IFrame/IFrame';
import RootDataViewLazy from '../../RootDataViewLazy/RootDataViewLazy';
import {loadCssListIframe1} from '../../../lib/CssListIframe1';
import {setCalendarDate} from "../../../redux/actions";
import Side from '../../Side/Side';

import React, {Component} from 'react'
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router";

export class IndexRow extends Component {

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
                <Side/>
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
)(IndexRow);
