/*
 * This file was last modified at 2021.03.21 17:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CreateTagLabel.jsx
 * $Id$
 */

import TagLabelView from './TagLabelView';
import tagLabel from "../../../lib/tagLabel";
import {
    getTagLabel,
    getTagLabelError,
    getTagLabelPending
} from "../../../reducers/tagLabel";

import React from 'react';
import {bindActionCreators, compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";

class CreateTagLabel extends TagLabelView {

    state = {
        data: {
            id: null,
            label: "",
            userName: null,
            createTime: "",
            updateTime: "",
            enabled: true,
            visible: true,
            flags: null,
            tags: null
        },
        redirectToReferrer: false,
    }

    constructor(props) {
        super(props);
        this.handler = this.handler.bind(this);
    }

    handler(){
        this.props.defaultActiveIndex();
    }

    setStateRedirectToReferrer1 = value => {
        console.log('CreateTagLabel.setStateRedirectToReferrer1');
        this.setState({redirectToReferrer: true});
    }

    handleSubmit = event => {
        console.log('CreateTagLabel.handleSubmit');
        event.preventDefault();
        this.props.tagLabel(this.state.data, this.setStateRedirectToReferrer1);
    }

    render() {
        console.log('CreateTagLabel.render state');
        console.log(this.state);
        console.log('CreateTagLabel.render props');
        console.log(this.props);
        const {redirectToReferrer} = this.state;
        if (redirectToReferrer === true) {
            this.handler();
        }
        if (this.state.data instanceof Promise) return (
            <div>Loading...</div>
        );
        return this.tagLabelView();
    }
}

const mapStateToProps = state => ({
    error: getTagLabelError(state),
    locale: state.language,
    pending: getTagLabelPending(state),
    tag: getTagLabel(state),
    user: state.currentUser,
})

const mapDispatchToProps = dispatch => bindActionCreators({
    tagLabel: tagLabel
}, dispatch)

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(CreateTagLabel);
