import React, { Component } from 'react'
import { Calendar } from 'primereact/calendar';
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router";

import './CalendarRow.scss'
import {setCalendarDate} from '../../redux/actions'
import IFrame from '../IFrame/IFrame'
import CalendarDataView from '../CalendarDataView/CalendarDataView'

const FILES = [
    '/css/theme.css',
    '/css/primeicons.css',
    '/css/main.362f8fd8.chunk.css'
]

export class CalendarRow extends Component {
  constructor(props) {
      super(props);
      const now = new Date();
      this.state = {
          calendarDate: {year: now.getFullYear(), month: now.getMonth() + 1, date: now.getDate()}
      };
  }

  componentDidMount() {
      var iframe1 = document.getElementById('iframe1'); // getElementsByTagName("iframe")[0].contentWindow;
      var head = iframe1.contentWindow.document.getElementsByTagName("head")[0];
      FILES.forEach(function(item, i, arr) {
          var cssLink = document.createElement("link", {href: item, rel: 'stylesheet', type: 'text/css'});
          cssLink.href = item;
          cssLink.rel = "stylesheet";
          cssLink.type = "text/css";
          head.appendChild(cssLink);
      });
  }

  calendarRowSetState(value) {
      const date = {year: value.getFullYear(), month: value.getMonth() + 1, date: value.getDate()}
      this.setState({ calendarDate: date })
      setCalendarDate(date)
  }

  render () {
    const divStyle = {
        borderStyle: 'none',
        width: '100%',
        height: '100%'
    };
    console.log("render: state.calendarDate: " + JSON.stringify(this.state.calendarDate))

    return (
        <div className="my-row">
            <div className="my-side">
                <h1>Calendar</h1>
                <Calendar dateFormat="yy-mm-dd"
                          value={this.state.calendarDate}
                          onChange={(e) => this.calendarRowSetState(e.value)}
                          inline
                          showWeek
                />
            </div>
            <div className="my-main">
                <IFrame style={divStyle} name='iframe1' id='iframe1'>
                    <CalendarDataView date={this.state.calendarDate} />
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
    setCalendarDate: value => dispatch(setCalendarDate(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(CalendarRow);
