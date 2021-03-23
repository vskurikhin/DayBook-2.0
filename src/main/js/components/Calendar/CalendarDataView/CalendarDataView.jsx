/*
 * This file was last modified at 2021.03.23 09:40 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CalendarDataView.jsx
 * $Id$
 */

import RenderTBody from "../../RenderItem/RenderTBody";
import isEmpty from "../../../lib/isEmpty";
import renderTitle from "../../RenderItem/renderTitle";
import {AllRecordService} from "../../../service/AllRecordService";
import {NUMBER_OF_ELEMENTS, TIMEOUT} from "../../../config/consts";
import {formatDate} from "../../../lib/formatDate";
import {setCalendarDate, setPage} from "../../../redux/actions";

import 'primeicons/primeicons.css';
import 'primereact/resources/primereact.min.css';
import 'primeflex/primeflex.css';
import React, {Component} from 'react';
import axios from "axios";
import {DataView} from 'primereact/dataview';
import {compose} from "redux";
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";

export class CalendarDataView extends Component {

    cancelTokenSource = axios.CancelToken.source();

    constructor(props) {
        super(props);
        this.update = this.update.bind(this);
        this.state = {
            first: 0,
            layout: 'list',
            loading: false,
            records: [],
            totalRecords: 0,
        };
        this.allRecordService = new AllRecordService(this.cancelTokenSource, this.props.date);
    }

    setRecords = value => {
        this.setState({
            ...this.state,
            loading: false,
            records: value['data'].content,
            totalRecords: value['data'].totalElements,
        });
    }

    update = () => {
        const event = {first: 0, page: 0};
        this.allRecordService.getRecordsLazy(event, NUMBER_OF_ELEMENTS, this.props.date)
            .then(this.setRecords)
            .catch(error => console.log(error));
    }

    componentDidMount() {
        this.update();
    }

    componentDidUpdate(prevProps) {
        if (this.props.date !== prevProps.date) {
            this.update();
        }
    }

    componentWillUnmount() {
        this.cancelTokenSource.cancel();
    }

    renderListItemEntity = value => {

        if (isEmpty(value)) {
            return (
                <div style={{border: 0}} />
            );
        }
        const {id, tags, publicTime, ...record} = value;

        return (
            <div style={{padding: '.5em', border: 0}} className="p-col-12 p-md-4">
                <div id={id}
                     className="p-panel p-component"
                     style={{textAlign: 'left'}}>
                    <div className="p-panel-header">
                        <span aria-label={id} className="p-panel-title"
                        >{renderTitle(id, record)}</span>
                        <div className="p-panel-icons"/>
                    </div>
                    <div aria-labelledby={id}
                         aria-hidden="false"
                         className="p-toggleable-content"
                         role="region">
                        <div className="p-panel-content">
                            <table aria-haspopup className="news-entry">
                                <RenderTBody
                                    id={id}
                                    publicTime={publicTime}
                                    tags={tags}
                                    value={record}
                                    windowWidth={window.innerWidth}
                                    {...this.props}
                                />
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    itemTemplate = (record, layout) => {
        if (!record) return;

        if (layout === 'grid')
            return this.renderListItemEntity(record);
        else if (layout === 'list')
            return this.renderListItemEntity(record);
    }

    render() {
        return (
            <div className="dataview-demo">
                <DataView first={this.state.first}
                          itemTemplate={this.itemTemplate}
                          layout={this.state.layout}
                          lazy
                          loading={this.state.loading}
                          rows={NUMBER_OF_ELEMENTS}
                          totalRecords={this.state.totalRecords}
                          value={this.state.records}
                />
            </div>
        );
    }
}

const mapStateToProps = state => ({
    date: formatDate(state.currentDate.currentDate),
    locale: state.language,
    user: state.currentUser,
})

const mapDispatchToProps = dispatch => ({
    handleCalendarDate: value => dispatch(setCalendarDate(value)),
    handlePage: value => dispatch(setPage(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(CalendarDataView);
