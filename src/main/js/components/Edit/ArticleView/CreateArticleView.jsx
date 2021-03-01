/*
 * This file was last modified at 2021.03.01 21:06 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CreateArticleView.jsx
 * $Id$
 */

import {API_V1_RESOURCE_NEWS_GROUPS, API_V1_RESOURCE_TAG_LABEL} from "../../../config/api";
import {ApiService} from "../../../service/ApiService";
import {DEFAULT_NEWS_GROUP_ID} from "../../../config/consts";
import {createArticle} from '../../../redux/actions';

import React, {Component} from 'react';
import axios from "axios";
import {AutoComplete} from 'primereact/autocomplete';
import {Button} from 'primereact/button';
import {Dropdown} from "primereact/dropdown";
import {InputTextarea} from 'primereact/inputtextarea';
import {InputText} from 'primereact/inputtext';
import {Redirect} from "react-router";
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";

class CreateArticleView extends Component {
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

    handleNewsGroupChange = value => {
        this.setState({newsGroupNames: value.data});
        const mayBeFirst = value.data.filter(x => x.id === DEFAULT_NEWS_GROUP_ID);
        this.setState({selectedNewsGroup: mayBeFirst.length > 0 ? mayBeFirst[0] : null})
    }

    handleTagLabelChange = value => {
        this.setState({tagLabels: value.data});
    }

    onChangeDefault = event => {
        this.setState({
            data: {
                ...this.state.data,
                [event.target.name]: event.target.value
            }
        });
    }

    onNewsGroupChange = (e) => {
        this.setState({selectedNewsGroup: e.value});
        this.setState({
            data: {
                ...this.state.data,
                newsGroupId: e.value.id
            }
        });
    }

    onTagLabelsChange = (e) => {
        this.setState({selectedTags: e.value});
        const tags = e.value.map(t => t.label);
        this.setState({
            data: {
                ...this.state.data,
                tags: tags
            }
        });
    }

    searchTagLabels = (event) => {
        setTimeout(() => {
            let filteredTagLabels;
            if ( ! event.query.trim().length) {
                filteredTagLabels = [...this.state.tagLabels];
            }
            else {
                filteredTagLabels = this.state.tagLabels.filter((tagLabel) => {
                    return tagLabel.label.toLowerCase().startsWith(event.query.toLowerCase());
                });
            }

            this.setState({filteredTagLabels: filteredTagLabels});
        }, 250);
    }

    componentDidMount() {
        this.newsGroupService.getAll(null, this.handleNewsGroupChange);
        this.tagLabelService.getAll(null, this.handleTagLabelChange);
    }

    componentWillUnmount() {
        this.cancelTokenSource.cancel();
    }

    render() {
        const redirectToReferrer = this.state.redirectToReferrer;
        if (redirectToReferrer === true) {
            return <Redirect to="/home" />
        }
        if (this.state.data instanceof Promise) return (
            <div>Loading...</div>
        );
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
                                        <label className="my-label"><b>Tags:</b></label><br/>
                                        <span className="p-float-label">
                                            <AutoComplete
                                                completeMethod={this.searchTagLabels}
                                                field="label"
                                                multiple onChange={this.onTagLabelsChange}
                                                style={{with: '100%'}}
                                                panelStyle={{with: '100%'}}
                                                suggestions={this.state.filteredTagLabels}
                                                value={this.state.selectedTags}
                                            />
                                        </span>
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
                                                onChange={this.onChangeDefault}
                                                style={{with: '100%'}}
                                                type="text"
                                                value={this.state.data.title}
                                            />
                                            <label htmlFor="title"><b>Title:</b></label>
                                        </span>
                                    </div>
                                    <div className="my-divTableCellRight">&nbsp;</div>
                                </div>

                                <div className="my-divTableRow">
                                    <div className="my-divTableCellLeft">&nbsp;</div>
                                    <div className="my-divTableCell">
                                        <span className="p-float-label">
                                            <InputText
                                                className="my-p-inputtext"
                                                id="include"
                                                name='include'
                                                onChange={this.onChangeDefault}
                                                type="text"
                                                value={this.state.data.include}
                                            />
                                            <label htmlFor="include"><b>Include:</b></label>
                                        </span>
                                    </div>
                                    <div className="my-divTableCellRight">&nbsp;</div>
                                </div>

                                <div className="my-divTableRow">
                                    <div className="my-divTableCellLeft">&nbsp;</div>
                                    <div className="my-divTableCell">
                                        <span className="p-float-label">
                                            <InputText
                                                className="my-p-inputtext"
                                                id="anchor"
                                                name='anchor'
                                                onChange={this.onChangeDefault}
                                                type="text"
                                                value={this.state.data.anchor}
                                            />
                                            <label htmlFor="anchor"><b>Anchor:</b></label>
                                        </span>
                                    </div>
                                    <div className="my-divTableCellRight">&nbsp;</div>
                                </div>

                                <div className="my-divTableRow">
                                    <div className="my-divTableCellLeft">&nbsp;</div>
                                    <div className="my-divTableCell">
                                        <label className="my-label"><b>Summary:</b></label><br/>
                                        <InputTextarea
                                            className="my-p-inputtext"
                                            autoResize
                                            cols={30}
                                            name='summary'
                                            onChange={this.onChangeDefault}
                                            rows={5}
                                            value={this.state.data.summary}
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
    createArticleView: value => dispatch(createArticle(value)),
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(CreateArticleView);
