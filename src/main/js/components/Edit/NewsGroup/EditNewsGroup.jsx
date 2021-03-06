
/*
 * This file was last modified at 2021.03.02 19:04 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * EditNewsGroup.jsx
 * $Id$
 */

import NewsGroupView from "./NewsGroupView";
import {updateNewsGroup} from "../../../redux/actions";

import {Redirect} from "react-router";
import {compose} from "redux";
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";
import React from "react";

class EditNewsGroup extends NewsGroupView {

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
        this.props.updateNewsGroupView(this.state.data);
        this.setState({redirectToReferrer: true});
    }

    render() {
        if (this.state.redirectToReferrer === true) {
            return <Redirect to="/index"/>
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
    updateNewsGroupView: value => dispatch(updateNewsGroup(value)),
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(EditNewsGroup);
