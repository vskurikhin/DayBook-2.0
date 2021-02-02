/*
 * This file was last modified at 2021.02.02 19:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CalendarRow.jsx
 * $Id$
 */

import React, {Component} from 'react'
import {ReactReduxContext} from 'react-redux'
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router";

import CalendarDataView from '../CalendarDataView/CalendarDataView'
import IFrame from '../IFrame/IFrame'
import {loadCssListIframe1} from '../../lib/CssListIframe1'
import {setCalendarDate} from '../../redux/actions'
import SimpleReactCalendar from 'simple-react-calendar'

export class CalendarRow extends Component {
    state = { count: 0 };

    constructor(props) {
        super(props);
        const now = new Date();
        this.state = {
            calendarDate: {year: now.getFullYear(), month: now.getMonth() + 1, date: now.getDate()}
        };
    }

    componentDidMount() {
        loadCssListIframe1();
        this.interval = setInterval(() => {
            this.setState(({ count }) => ({ count: count + 1 }));
        }, 1000);
    }

    calendarRowSetState(value) {
        const date = {year: value.getFullYear(), month: value.getMonth() + 1, date: value.getDate()}
        console.log(new Intl.DateTimeFormat('en-US', {year: 'numeric', month: '2-digit',day: '2-digit', hour: '2-digit', minute: '2-digit', second: '2-digit'}).format(timestamp));
        console.log("date:" + date);
        this.setState({calendarDate: date})
        this.props.handleCalendarDate(date)
    }

    getDocFinancialInfo = timestamp => {
        this.calendarRowSetState(timestamp);
        this.setState({ state: Math.random() });
    };

    renderCalendar(store) {
        let calendarDate = store.getState().calendarDate.calendarDate
        let stateDate = new Date(calendarDate.year, calendarDate.month - 1, calendarDate.date)

        return (
            <SimpleReactCalendar activeMonth={new Date()} onSelect={this.getDocFinancialInfo} />
        )
    }

    render() {
        const divStyle = {
            borderStyle: 'none',
            width: '100%',
            height: '100%'
        };
        console.log("!!!OK!!!");

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
                        <CalendarDataView date={this.state.calendarDate}/>
                    </IFrame>
                </div>
            </div>
        )
    }
}

const mapStateToProps = state => ({
    calendarDate: state.calendarDate
})

const mapDispatchToProps = dispatch => ({
    handleCalendarDate: value => dispatch(setCalendarDate(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(CalendarRow);
