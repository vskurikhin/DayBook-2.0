/*
 * This file was last modified at 2021.03.02 23:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * EditRow.jsx
 * $Id$
 */

import EditArticleView from "./Article/EditArticle";
import EditNewsEntryView from "./NewsEntry/EditNewsEntry";
import IFrame from "../IFrame/IFrame";
import Side from '../Side/Side';
import {loadCssListIframe1} from "../../lib/CssListIframe1";
import {setCalendarDate} from "../../redux/actions";

import React, {Component} from 'react';
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router";

const isArticle = object => object === 'article';
const isNewsEntry = object => object === 'news-entry';
const isNewsLinks = object => object === 'news-links';

class EditRow extends Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        loadCssListIframe1()
    }

    getObject() {
        if (this.props.match.params !== undefined) {
            return this.props.match.params.object;
        }
        return null;
    }

    renderEdit(object) {
        if (isArticle(object)) return (
            <EditArticleView/>
        );
        if (isNewsEntry(object)) return (
            <EditNewsEntryView/>
        );
        if (isNewsLinks(object)) return (
            <EditNewsEntryView/>
        );
        return (
            <div/>
        );
    }

    render() {
        const divStyle = {
            borderStyle: 'none',
            width: '100%',
            height: '100%'
        };
        const object = this.getObject();

        return (
            <div className="my-row">
                <div className="my-side">
                    <Side/>
                </div>
                <div className="my-main" id='main'>
                    <IFrame style={divStyle} name='iframe1' id='iframe1'>
                        {this.renderEdit(object)}
                    </IFrame>
                </div>
            </div>
        )
    }
}

const mapStateToProps = state => ({
    user: state.currentUser,
    date: state.currentDate
})

const mapDispatchToProps = dispatch => ({
    handleCalendarDate: value => dispatch(setCalendarDate(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(EditRow);
