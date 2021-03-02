/*
 * This file was last modified at 2021.03.02 19:04 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CreateTagLabel.jsx
 * $Id$
 */

import TagLabelView from './TagLabelView';
import {createTagLabel} from '../../../redux/actions';

import React from 'react';
import {Redirect} from "react-router";
import {compose} from "redux";
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
        console.log(props);
    }

    handler(){
        this.props.defaultActiveIndex();
    }

    handleSubmit = event => {
        event.preventDefault();
        this.props.createTagLabelView(this.state.data);
        this.setState({redirectToReferrer: true});
    }

    render() {
        if (this.state.redirectToReferrer === true) {
            this.handler();
        }
        if (this.state.data instanceof Promise) return (
            <div>Loading...</div>
        );
        return this.tagLabelView();
    }
}

const mapStateToProps = state => ({
    user: state.currentUser,
})

const mapDispatchToProps = dispatch => ({
    createTagLabelView: value => dispatch(createTagLabel(value)),
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(CreateTagLabel);
