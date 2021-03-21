/*
 * This file was last modified at 2021.03.21 17:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * EditNewsEntry.jsx
 * $Id$
 */

import './DropdownDemo.css';
import NewsEntryView from './NewsEntryView';
import {API_V1_RESOURCE_NEWS_GROUPS, API_V1_RESOURCE_TAG_LABEL} from "../../../config/api";
import {ApiService} from "../../../service/ApiService";
import {putNewsEntryRecord} from '../../../lib/resourceRecord';
import {recordService} from '../../../service/RecordService';
import {
    getResourceRecord,
    getResourceRecordError,
    getResourceRecordPending
} from "../../../reducers/resourceRecord";

import React from 'react';
import axios from 'axios';
import {Redirect} from "react-router";
import {bindActionCreators, compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";

class EditNewsEntry extends NewsEntryView {

    state = {
        data: {
            id: null,
            newsGroupId: null,
            title: "",
            content: "",
            publicTime: null,
            visible: true,
            tags: null
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

    handleSubmit = event => {
        event.preventDefault();
        this.props.putRecord(this.state.data, this.setStateRedirectToReferrerRecord);
    }

    onFinallyNewsGroupService = () => {
        const {id} = this.props.match.params;
        return recordService.getNewsEntry(id, this.handleRecordChange, this.cancelTokenSource);
    }

    componentDidMount() {
        this.newsGroupService.getAll(null, this.handleEditNewsGroupChange)
            .finally(this.onFinallyNewsGroupService);
        this.tagLabelService.getAll(null, this.handleTagLabelChange);
    }

    componentWillUnmount() {
        this.cancelTokenSource.cancel();
    }

    render() {
        const {pending} = this.props;
        const {redirectToReferrer} = this.state;
        if (redirectToReferrer === true) {
            return <Redirect to="/index"/>
        }
        if (pending) return (
            <div>Loading...</div>
        );
        return this.newsEntryView();
    }
}

const mapStateToProps = state => ({
    error: getResourceRecordError(state),
    locale: state.language,
    pending: getResourceRecordPending(state),
    record: getResourceRecord(state),
    user: state.currentUser,
})

const mapDispatchToProps = dispatch => bindActionCreators({
    putRecord: putNewsEntryRecord
}, dispatch)

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(EditNewsEntry);
