/*
 * This file was last modified at 2021.02.27 00:06 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CreateArticleView.jsx
 * $Id$
 */

import {API_V1_RESOURCE_NEWS_GROUPS} from "../../../config/api";
import {DEFAULT_NEWS_GROUP_ID} from "../../../config/consts";
import {adminCreateArticle} from '../../../redux/actions';
import './DropdownDemo.css';

import React, {Component} from 'react';
import axios from "axios";
import {ApiService} from "../../../service/ApiService";
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
        content: "",
        newsGroupId: DEFAULT_NEWS_GROUP_ID,
        newsGroupNames: [],
        redirectToReferrer: false,
        selectedNewsGroup: "",
        title: "",
    }
    cancelTokenSource = axios.CancelToken.source();
    newsGroupService = new ApiService(API_V1_RESOURCE_NEWS_GROUPS + '/all', this.cancelTokenSource);

    constructor(props) {
        super(props);
    }

    onNewsGroupChange = (e) => {
        this.setState({selectedNewsGroup: e.value});
        this.setState({newsGroupId: e.value.id});
    }

    handleChange = event => {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    handleSubmit = event => {
        event.preventDefault()
        this.props.adminCreateArticleView(this.state)
        this.setState({redirectToReferrer: true})
    }

    handleNewsGroupChange = value => {
        this.setState({newsGroupNames: value.data});
        const mayBeFirst = value.data.filter(x => x.id === DEFAULT_NEWS_GROUP_ID);
        this.setState({selectedNewsGroup: mayBeFirst.length > 0 ? mayBeFirst[0] : null})
    }

    componentDidMount() {
        this.newsGroupService.getAll(null, this.handleNewsGroupChange);
    }

    componentWillUnmount() {
        this.cancelTokenSource.cancel();
    }

    render() {
        const redirectToReferrer = this.state.redirectToReferrer;
        if (redirectToReferrer === true) {
            return <Redirect to="/home" />
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
                                        <span className="p-float-label">
                                            <InputText
                                                className="my-p-inputtext"
                                                id="include"
                                                name='include'
                                                onChange={this.handleChange}
                                                type="text"
                                                value={this.state.include}
                                            />
                                            <label htmlFor="include"><b>include</b></label>
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
                                                onChange={this.handleChange}
                                                type="text"
                                                value={this.state.anchor}
                                            />
                                            <label htmlFor="anchor"><b>anchor</b></label>
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
                                            onChange={this.handleChange}
                                            rows={5}
                                            value={this.state.summary}
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
    adminCreateArticleView: value => dispatch(adminCreateArticle(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(CreateArticleView);
