/*
 * This file was last modified at 2021.02.26 10:44 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AdminCreateNewsEntryView.jsx
 * $Id$
 */

import {API_V1_RESOURCE_NEWS_GROUPS} from '../../config/api';
import {ApiService} from '../../service/ApiService';
import {adminCreateNewsEntry} from '../../redux/actions';

import React, {Component} from 'react';
import axios from "axios";
import {Button} from 'primereact/button';
import {InputMask} from 'primereact/inputmask';
import {InputTextarea} from 'primereact/inputtextarea';
import {InputText} from 'primereact/inputtext';
import {Redirect} from "react-router";
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";

class AdminCreateNewsEntryView extends Component {

    state = {
        newsGroupId: "00000000-0000-0000-0000-000000000001",
        title: "",
        content: "",
        redirectToReferrer: false,
        data: {
            id: null,
            groupName: "",
            userName: null,
            createTime: "",
            updateTime: "",
            enabled: true,
            visible: true,
            flags: null,
        },
    }
    cancelTokenSource = axios.CancelToken.source();
    newsGroupService = new ApiService(API_V1_RESOURCE_NEWS_GROUPS, this.cancelTokenSource);

    constructor(props) {
        super(props);
    }

    handleChange = event => {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    handleSubmit = event => {
        event.preventDefault()
        this.props.adminCreateNewsEntry(this.state)
        this.setState({redirectToReferrer: true})
    }

    handleSubscriptionChange = dataSource => {
        this.setState(dataSource);
    }

    componentDidMount() {
        console.log('AdminCreateNewsEntryView.componentDidMount');
        console.log(this.props);
        this.newsGroupService.getAll(null, this.handleSubscriptionChange);
    }

    componentWillUnmount() {
        console.log('AdminCreateNewsEntryView.componentWillUnmount');
        this.cancelTokenSource.cancel();
    }

    render() {
        if (this.state.redirectToReferrer === true) {
            return <Redirect to="/home"/>
        }
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
                                            value={this.state.newsGroupId}
                                        />
                                    </div>
                                    <div className="my-divTableCellRight">&nbsp;</div>
                                </div>

                                <div className="my-divTableRow">
                                    <div className="my-divTableCellLeft">&nbsp;</div>
                                    <div className="my-divTableCell">
                                        <span className="p-float-label">
                                            <InputText
                                                className="my-p-inputtext"
                                                id="title"
                                                name='title'
                                                onChange={this.handleChange}
                                                type="text"
                                                value={this.state.title}
                                            />
                                            <label htmlFor="title"><b>title</b></label>
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
                                            value={this.state.content}
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
    user: state.currentUser,
    date: state.currentDate
})

const mapDispatchToProps = dispatch => ({
    adminCreateNewsEntry: value => dispatch(adminCreateNewsEntry(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(AdminCreateNewsEntryView);
