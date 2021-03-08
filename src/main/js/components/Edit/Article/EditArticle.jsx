/*
 * This file was last modified at 2021.03.07 23:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * EditArticle.jsx
 * $Id$
 */

import ArticleView from './ArticleView';
import {API_V1_RESOURCE_NEWS_GROUPS, API_V1_RESOURCE_TAG_LABEL} from "../../../config/api";
import {ApiService} from "../../../service/ApiService";
import {recordService} from '../../../service/RecordService';
import {updateArticle} from '../../../redux/actions';

import React from 'react';
import axios from 'axios';
import {Redirect} from "react-router";
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";

class EditArticle extends ArticleView {

    state = {
        data: {
            id: null,
            newsGroupId: null,
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
        this.props.editArticleView(this.state.data)
        this.setState({redirectToReferrer: true})
    }

    componentDidMount() {
        this.newsGroupService.getAll(null, this.handleEditNewsGroupChange).finally(
            () => recordService.getArticle(
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
        return this.articleView();
    }
}

const mapStateToProps = state => ({
    user: state.currentUser,
})

const mapDispatchToProps = dispatch => ({
    editArticleView: value => dispatch(updateArticle(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(EditArticle);
