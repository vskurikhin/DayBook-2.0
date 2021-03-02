/*
 * This file was last modified at 2021.03.02 17:18 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CreateArticle.jsx
 * $Id$
 */

import ArticleView from './ArticleView';
import {API_V1_RESOURCE_NEWS_GROUPS, API_V1_RESOURCE_TAG_LABEL} from "../../../config/api";
import {ApiService} from "../../../service/ApiService";
import {DEFAULT_NEWS_GROUP_ID} from "../../../config/consts";
import {createArticle} from '../../../redux/actions';

import React from 'react';
import axios from "axios";
import {Redirect} from "react-router";
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";

class CreateArticle extends ArticleView {
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
        newsGroupId: DEFAULT_NEWS_GROUP_ID,
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
        event.preventDefault()
        this.props.createArticleView(this.state.data)
        this.setState({redirectToReferrer: true})
    }

    componentDidMount() {
        this.newsGroupService.getAll(null, this.handleCreateNewsGroupChange);
        this.tagLabelService.getAll(null, this.handleTagLabelChange);
    }

    componentWillUnmount() {
        this.cancelTokenSource.cancel();
    }

    render() {
        const redirectToReferrer = this.state.redirectToReferrer;
        if (redirectToReferrer === true) {
            return <Redirect to="/index" />
        }
        if (this.state.data instanceof Promise) return (
            <div>Loading...</div>
        );
        return this.articleView();
    }
}

const mapStateToProps = state => ({
    user: state.currentUser,
})

const mapDispatchToProps = dispatch => ({
    createArticleView: value => dispatch(createArticle(value)),
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(CreateArticle);
