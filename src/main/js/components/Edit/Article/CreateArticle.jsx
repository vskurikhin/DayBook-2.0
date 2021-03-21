/*
 * This file was last modified at 2021.03.21 13:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CreateArticle.jsx
 * $Id$
 */

import ArticleView from './ArticleView';
import {API_V1_RESOURCE_NEWS_GROUPS, API_V1_RESOURCE_TAG_LABEL} from "../../../config/api";
import {ApiService} from "../../../service/ApiService";
import {DEFAULT_NEWS_GROUP_ID} from "../../../config/consts";
import {postArticleRecord} from "../../../lib/resourceRecord";
import {
    getResourceRecord,
    getResourceRecordError,
    getResourceRecordPending
} from "../../../reducers/resourceRecord";

import 'primeicons/primeicons.css';
import 'primereact/resources/primereact.min.css';
import 'primeflex/primeflex.css';
import React from 'react';
import axios from "axios";
import {Redirect} from "react-router";
import {bindActionCreators, compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";

class CreateArticle extends ArticleView {

    state = {
        data: {
            id: null,
            newsGroupId: DEFAULT_NEWS_GROUP_ID,
            title: "",
            include: "",
            anchor: "",
            summary: "",
            publicTime: null,
            visible: true,
            tags: null
        },
        newsGroupNames: [],
        redirectToReferrer: false,
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
        this.props.postRecord(this.state.data, this.setStateRedirectToReferrer);
    }

    componentDidMount() {
        this.newsGroupService.getAll(null, this.handleCreateNewsGroupChange);
        this.tagLabelService.getAll(null, this.handleTagLabelChange);
    }

    componentWillUnmount() {
        this.cancelTokenSource.cancel();
    }

    render() {
        const {pending} = this.props;
        const {redirectToReferrer} = this.state;
        if (redirectToReferrer === true) {
            return <Redirect to="/index" />
        }
        if (pending) return (
            <div>Loading...</div>
        );
        return this.articleView();
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
    postRecord: postArticleRecord
}, dispatch)

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(CreateArticle);
