/*
 * This file was last modified at 2021.03.21 13:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CreateNewsGroup.jsx
 * $Id$
 */

import NewsGroupView from './NewsGroupView';
import {createNewsGroup} from '../../../redux/actions';

import React from 'react';
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";

class CreateNewsGroup extends NewsGroupView {

    state = {
        data: {
            id: null,
            groupName: "",
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
    }

    handleSubmit = event => {
        event.preventDefault();
        this.props.createNewsGroupView(this.state.data);
        this.setState({redirectToReferrer: true});
    }

    render() {
        if (this.state.redirectToReferrer === true) {
            // return <Redirect to="/create"/>
            this.props.defaultActiveIndex();
        }
        if (this.state.data instanceof Promise) return (
            <div>Loading...</div>
        );
        return this.newsGroupView();
    }
}

const mapStateToProps = state => ({
    user: state.currentUser,
})

const mapDispatchToProps = dispatch => ({
    createNewsGroupView: value => dispatch(createNewsGroup(value)),
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(CreateNewsGroup);
