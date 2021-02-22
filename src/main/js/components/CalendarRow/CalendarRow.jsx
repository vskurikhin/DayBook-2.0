/*
 * This file was last modified at 2021.02.22 22:44 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CalendarRow.jsx
 * $Id$
 */

import CalendarDataView from '../CalendarDataView/CalendarDataView'
import IFrame from '../IFrame/IFrame'
import SimpleReactCalendar from 'simple-react-calendar'
import {formatDate, logDate} from '../../lib/formatDate'
import {loadCssListIframe1} from '../../lib/CssListIframe1'
import {setCalendarDate} from '../../redux/actions'

import React, {Component} from 'react'
import {compose} from "redux";
import {connect, ReactReduxContext} from 'react-redux'
import {withRouter} from "react-router";

export class CalendarRow extends Component {

    constructor(props) {
        super(props);
        this.state = {
            calendarDate: this.getCurrentDate()
        };
    }

    componentDidMount() {
        loadCssListIframe1();
    }

    getCurrentUser() {
        return this.props.currentUser['currentUser'];
    }

    getCurrentDate() {
        return this.props.currentDate['currentDate'];
    }

    calendarRowSetState(date) {
        this.state = {calendarDate: date};
        this.setState({calendarDate: date})
        this.props.handleCalendarDate(date)
    }

    getDocFinancialInfo = timestamp => {
        const date = {year: timestamp.getFullYear(), month: timestamp.getMonth() + 1, date: timestamp.getDate()}
        logDate("CalendarRow.getDocFinancialInfo: ", date);
        this.calendarRowSetState(date);
    };

    renderCalendar(store) {
        let calendarDate = store.getState().currentDate.currentDate;
        const selectedDate = new Date(calendarDate.year, calendarDate.month - 1, calendarDate.date);

        return (
            <SimpleReactCalendar
                mode="single"
                activeMonth={selectedDate}
                selected={selectedDate}
                onSelect={this.getDocFinancialInfo}
            />
        )
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
                    <h1>Calendar</h1>
                    <ReactReduxContext.Consumer>
                        { ({store}) => this.renderCalendar(store) }
                    </ReactReduxContext.Consumer>
                </div>
                <div className="my-main">
                    <IFrame style={divStyle} name='iframe1' id='iframe1'>
                        <CalendarDataView date={formatDate(this.getCurrentDate())}/>
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
)(CalendarRow);
