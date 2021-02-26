/*
 * This file was last modified at 2021.02.26 10:44 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AdminEditNewsEntryView.jsx
 * $Id$
 */

import {adminUpdateNewsEntry} from '../../redux/actions';
import {recordService} from '../../service/RecordService';

import React, {Component} from 'react';
import axios from 'axios';
import {Button} from "primereact/button";
import {InputMask} from "primereact/inputmask";
import {InputTextarea} from "primereact/inputtextarea";
import {InputText} from "primereact/inputtext";
import {Redirect} from "react-router";
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";

class AdminEditNewsEntryView extends Component {

    state = {
        data: {
            id: null,
            newsGroupId: "00000000-0000-0000-0000-000000000001",
            title: "",
            content: "",
            userName: null,
            createTime: "",
            updateTime: "",
            enabled: true,
            visible: true,
            flags: null,
        },
        redirectToReferrer: false
    };
    cancelTokenSource = axios.CancelToken.source();

    constructor(props) {
        super(props);
    }

    handleChange = event => {
        this.setState({
            data: {
                ...this.state.data,
                [event.target.name]: event.target.value
            }
        });
    }

    componentDidMount() {
        console.log('AdminEditView.componentDidMount');
        console.log(this.props);
        recordService.getNewsEntry(this.props.match.params.id, this.handleSubscriptionChange, this.cancelTokenSource);
    }

    componentWillUnmount() {
        this.cancelTokenSource.cancel();
    }

    handleSubscriptionChange = dataSource => {
        this.setState(dataSource);
    }

    handleSubmit = event => {
        event.preventDefault()
        console.log(this.state);
        this.props.adminUpdateNewsEntry(this.state.data)
        this.setState({redirectToReferrer: true})
    }

    render() {
        if (this.state.redirectToReferrer === true) {
            return <Redirect to="/home"/>
        }
        if (this.state.data instanceof Promise) return (
            <div>Loading...</div>
        );
        console.log(this.state);
        return (
            <div className="dataview-demo">
                <form onSubmit={this.handleSubmit}>
                    <div className="card">
                        <div className="my-divTable">
                            <div className="my-divTableBody">
                                <div className="my-divTableRow">
                                    <div className="my-divTableCellLeft">&nbsp;</div>
                                    <div className="my-divTableCell">
                                        <label className="my-label"><b>News group Id:</b></label><br/>
                                        <InputMask
                                            className="my-p-inputtext-uuid"
                                            id="inputmask"
                                            mask="********-****-****-****-************"
                                            name='newsGroupId'
                                            onChange={this.handleChange}
                                            slotChar="00000000-0000-0000-0000-000000000001"
                                            value={this.state.data.newsGroupId}
                                        />
                                    </div>
                                    <div className="my-divTableCellRight">&nbsp;</div>
                                </div>
                                <div className="my-divTableRow">
                                    <div className="my-divTableCellLeft">&nbsp;</div>
                                    <div className="my-divTableCell">
                                        <label className="my-label"><b>Title:</b></label><br/>
                                        <span className="p-float-label">
                                            <InputText
                                                className="my-p-inputtext"
                                                id="title"
                                                name='title'
                                                onChange={this.handleChange}
                                                type="text"
                                                value={this.state.data.title}
                                            />
                                        </span>
                                    </div>
                                    <div className="my-divTableCellRight">&nbsp;</div>
                                </div>
                                <div className="my-divTableRow">
                                    <div className="my-divTableCellLeft">&nbsp;</div>
                                    <div className="my-divTableCell">
                                        <label className="my-label"><b>Content:</b></label><br/>
                                        <InputTextarea
                                            className="my-p-inputtext"
                                            autoResize
                                            cols={30}
                                            name='content'
                                            onChange={this.handleChange}
                                            rows={5}
                                            value={this.state.data.content}
                                        />
                                    </div>
                                    <div className="my-divTableCellRight">&nbsp;</div>
                                </div>
                                <div className="my-divTableRow">
                                    <div className="my-divTableCellLeft">&nbsp;</div>
                                    <div className="my-divTableCell">
                                        <Button
                                            className="my-p-button"
                                            icon="pi pi-check"
                                            iconPos="right"
                                            label="Submit"
                                        />
                                    </div>
                                    <div className="my-divTableCellRight">&nbsp;</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        );
    }
}

const mapStateToProps = state => ({
    ...state.currentUser,
    ...state.currentDate
})

const mapDispatchToProps = dispatch => ({
    adminUpdateNewsEntry: value => dispatch(adminUpdateNewsEntry(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(AdminEditNewsEntryView);
