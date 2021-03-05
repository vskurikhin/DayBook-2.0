/*
 * This file was last modified at 2021.03.04 13:41 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Upload.jsx
 * $Id$
 */

import UploadView from './UploadView';
import {createTagLabel} from '../../../redux/actions';

import React from 'react';
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";

class Upload extends UploadView {

    state = {
        toast: null,
        redirectToReferrer: false,
    }

    constructor(props) {
        super(props);
        this.handler = this.handler.bind(this);
    }

    handler() {
        this.props.defaultActiveIndex();
    }

    onBeforeSend(event) {
        console.log(event);
        const token = window.sessionStorage.token;
        event.xhr.setRequestHeader('Authorization', `Bearer ${token}`);
    }

    render() {
        if (this.state.redirectToReferrer === true) {
            this.handler();
        }
        if (this.state.data instanceof Promise) return (
            <div>Loading...</div>
        );
        return this.uploadView();
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
)(Upload);
