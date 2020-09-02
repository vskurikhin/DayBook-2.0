import React, {Component} from 'react'
import {ReactReduxContext} from 'react-redux'
import {Calendar} from 'primereact/calendar';
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router";

import './CalendarRow.scss'
import CalendarDataView from '../CalendarDataView/CalendarDataView'
import IFrame from '../IFrame/IFrame'
import {loadCssListIframe1} from '../../lib/CssListIframe1'
import {setCalendarDate} from '../../redux/actions'

export class CalendarRow extends Component {
    constructor(props) {
        super(props);
        const now = new Date();
        this.state = {
            calendarDate: {year: now.getFullYear(), month: now.getMonth() + 1, date: now.getDate()}
        };
    }

    componentDidMount() {
        loadCssListIframe1()
    }

    calendarRowSetState(value) {
        const date = {year: value.getFullYear(), month: value.getMonth() + 1, date: value.getDate()}
        this.setState({calendarDate: date})
        this.props.handleCalendarDate(date)
    }

    renderCalendar(store) {
        let calendarDate = store.getState().calendarDate.calendarDate
        let stateDate = new Date(calendarDate.year, calendarDate.month - 1, calendarDate.date)

        return (
            <Calendar dateFormat="yy-mm-dd"
                      value={stateDate}
                      onChange={(e) => this.calendarRowSetState(e.value)}
                      inline
                      showWeek
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
