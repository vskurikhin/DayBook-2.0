/*
 * This file was last modified at 2021.03.23 09:40 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Upload.jsx
 * $Id$
 */

import UploadView from './UploadView';

import React from 'react';

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
