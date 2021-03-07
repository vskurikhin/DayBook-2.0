/*
 * This file was last modified at 2021.03.07 23:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * EditNewsEntry.jsx
 * $Id$
 */

import './DropdownDemo.css';
import NewsEntryView from './NewsEntryView';
import {API_V1_RESOURCE_NEWS_GROUPS, API_V1_RESOURCE_TAG_LABEL} from "../../../config/api";
import {ApiService} from "../../../service/ApiService";
import {recordService} from '../../../service/RecordService';
import {updateNewsEntry} from '../../../redux/actions';

import React from 'react';
import axios from 'axios';
import {Redirect} from "react-router";
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";
import {DEFAULT_NEWS_GROUP_ID} from "../../../config/consts";

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
        this.props.editNewsEntryView(this.state.data);
        this.setState({redirectToReferrer: true});
    }

    componentDidMount() {
        this.newsGroupService.getAll(null, this.handleEditNewsGroupChange).finally(
            () => recordService.getNewsEntry(
                this.props.match.params.id,
                this.handleRecordChange,
                this.cancelTokenSource)
        );
        this.tagLabelService.getAll(null, this.handleTagLabelChange);
    }

    componentWillUnmount() {
        this.cancelTokenSource.cancel();
    }

    render() {
        if (this.state.redirectToReferrer === true) {
            return <Redirect to="/index"/>
        }
        if (this.state.data instanceof Promise) return (
            <div>Loading...</div>
        );
        const values = {};
        return this.newsEntryView();
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
)(EditNewsEntry);
