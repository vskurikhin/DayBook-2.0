/*
 * This file was last modified at 2021.03.11 18:14 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CalendarRow.jsx
 * $Id$
 */

import CalendarDataView from '../CalendarDataView/CalendarDataView'
import IFrame from '../../IFrame/IFrame'
import SimpleReactCalendar from 'simple-react-calendar'
import {formatDate, logDate} from '../../../lib/formatDate'
import {loadCssListIframe1} from '../../../lib/CssListIframe1'
import {locales} from "../../../config/locales";
import {setCalendarDate} from '../../../redux/actions'

import React, {Component} from 'react'
import {compose} from "redux";
import {connect, ReactReduxContext} from 'react-redux'
import {withRouter} from "react-router";
import {Text, TranslationsProvider} from "@eo-locale/react";

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

    getCurrentDate() {
        return this.props.date['currentDate'];
    }

    calendarRowSetState(date) {
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
        const daysOfWeek = locales
            .filter(l => l.language === this.props.locale.language)
            .map(l => l.messages.daysOfWeek)[0];
        console.log('renderCalendar');
        console.log(daysOfWeek);

        return (
            <SimpleReactCalendar
                activeMonth={selectedDate}
                daysOfWeek={daysOfWeek}
                mode="single"
                onSelect={this.getDocFinancialInfo}
                selected={selectedDate}
            />
        )
    }

    render() {
        const divStyle = {
            borderStyle: 'none',
            width: '100%',
            height: '100%'
        };
        window.__localeId__ = 'ru'

        return (
            <div className="my-row">
                <TranslationsProvider language={this.props.locale.language} locales={locales}>
                <div className="my-side">
                    <h1><Text id='Calendar'/></h1>
                    <ReactReduxContext.Consumer>
                        { ({store}) => this.renderCalendar(store) }
                    </ReactReduxContext.Consumer>
                </div>
                <div className="my-main">
                    <IFrame style={divStyle} name='iframe1' id='iframe1'>
                        <CalendarDataView date={formatDate(this.getCurrentDate())}/>
                    </IFrame>
                </div>
                </TranslationsProvider>
            </div>
        )
    }
}

const mapStateToProps = state => ({
    date: state.currentDate,
    locale: state.language
})

const mapDispatchToProps = dispatch => ({
    handleCalendarDate: value => dispatch(setCalendarDate(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(CalendarRow);
