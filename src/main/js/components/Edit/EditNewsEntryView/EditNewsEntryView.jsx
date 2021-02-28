/*
 * This file was last modified at 2021.02.28 23:25 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * EditNewsEntryView.jsx
 * $Id$
 */

import {API_V1_RESOURCE_NEWS_GROUPS, API_V1_RESOURCE_TAG_LABEL} from "../../../config/api";
import {updateNewsEntry} from '../../../redux/actions';
import {recordService} from '../../../service/RecordService';

import React, {Component} from 'react';
import axios from 'axios';
import {ApiService} from "../../../service/ApiService";
import {Button} from "primereact/button";
import {Dropdown} from "primereact/dropdown";
import {InputTextarea} from "primereact/inputtextarea";
import {InputText} from "primereact/inputtext";
import {Redirect} from "react-router";
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";

class EditNewsEntryView extends Component {

    state = {
        data: {
            id: null,
            newsGroupId: null,
            title: "",
            content: "",
            userName: null,
            createTime: "",
            updateTime: "",
            enabled: true,
            visible: true,
            flags: null,
        },
        redirectToReferrer: false,
        newsGroupNames: [],
        selectedNewsGroup: "",
        filteredTagLabels: [],
        tagLabels: [],
        selectedTags: []
    };
    cancelTokenSource = axios.CancelToken.source();
    newsGroupService = new ApiService(API_V1_RESOURCE_NEWS_GROUPS + '/all', this.cancelTokenSource);
    tagLabelService = new ApiService(API_V1_RESOURCE_TAG_LABEL + '/all', this.cancelTokenSource);

    constructor(props) {
        super(props);
    }

    handleChange = event => {
        this.setState({
            data: {
                ...this.state.data,
                [event.target.name]: event.target.value,
                tags: ["label1", "label2", "label3"]
            }
        });
    }

    onNewsGroupChange = (e) => {
        this.setState({selectedNewsGroup: e.value});
        this.setState({data: {
                ...this.state.data,
                newsGroupId: e.value.id
            }
        });
    }

    handleSubscriptionChange = value => {
        this.setState({data: value.data});
        this.mayBeSetSelectedNewsGroup();
    }

    handleNewsGroupChange = value => {
        this.setState({newsGroupNames: value.data});
    }

    mayBeSetSelectedNewsGroup = () => {
        const mayBeItem = this.state.newsGroupNames.filter(x => x.id === this.state.data.newsGroupId);
        this.setState({selectedNewsGroup: mayBeItem.length > 0 ? mayBeItem[0] : null})
    }

    handleSubmit = event => {
        event.preventDefault();
        this.props.editNewsEntryView(this.state.data)
        this.setState({redirectToReferrer: true})
    }

    componentDidMount() {
        this.newsGroupService.getAll(null, this.handleNewsGroupChange).finally(
            () => recordService.getNewsEntry(
                this.props.match.params.id,
                this.handleSubscriptionChange,
                this.cancelTokenSource)
        );
    }

    componentWillUnmount() {
        this.cancelTokenSource.cancel();
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
                                        <Dropdown
                                            onChange={this.onNewsGroupChange}
                                            optionLabel="groupName"
                                            options={this.state.newsGroupNames}
                                            placeholder="Select a News group"
                                            value={this.state.selectedNewsGroup}
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
    user: state.currentUser,
})

const mapDispatchToProps = dispatch => ({
    editNewsEntryView: value => dispatch(updateNewsEntry(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(EditNewsEntryView);
