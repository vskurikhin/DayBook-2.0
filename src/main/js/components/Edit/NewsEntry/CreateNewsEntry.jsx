/*
 * This file was last modified at 2021.03.01 23:42 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CreateNewsEntry.jsx
 * $Id$
 */

import './DropdownDemo.css';
import NewsEntryView from './NewsEntryView';
import {API_V1_RESOURCE_NEWS_GROUPS, API_V1_RESOURCE_TAG_LABEL} from '../../../config/api';
import {ApiService} from '../../../service/ApiService';
import {DEFAULT_NEWS_GROUP_ID} from '../../../config/consts';
import {createNewsEntry} from '../../../redux/actions';

import React from 'react';
import axios from "axios";
import {Redirect} from "react-router";
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";

class CreateNewsEntry extends NewsEntryView {

    state = {
        data: {
            id: null,
            newsGroupId: DEFAULT_NEWS_GROUP_ID,
            title: "",
            content: "",
            userName: null,
            createTime: "",
            updateTime: "",
            enabled: true,
            visible: true,
            flags: null,
            tags: null
        },
        newsGroupNames: [],
        redirectToReferrer: false,
        selectedNewsGroup: "",
        filteredTagLabels: [],
        tagLabels: [],
        selectedTags: []
    }
    cancelTokenSource = axios.CancelToken.source();
    newsGroupService = new ApiService(API_V1_RESOURCE_NEWS_GROUPS + '/all', this.cancelTokenSource);
    tagLabelService = new ApiService(API_V1_RESOURCE_TAG_LABEL + '/all', this.cancelTokenSource);

    constructor(props) {
        super(props);
    }

    handleSubmit = event => {
        event.preventDefault();
        this.props.createNewsEntryView(this.state.data);
        this.setState({redirectToReferrer: true});
    }

    componentDidMount() {
        this.newsGroupService.getAll(null, this.handleCreateNewsGroupChange);
        this.tagLabelService.getAll(null, this.handleTagLabelChange);
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
        return this.newsEntryView();
    }
}

const mapStateToProps = state => ({
    user: state.currentUser,
})

const mapDispatchToProps = dispatch => ({
    createNewsEntryView: value => dispatch(createNewsEntry(value)),
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(CreateNewsEntry);
