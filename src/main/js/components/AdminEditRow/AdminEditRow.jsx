/*
 * This file was last modified at 2021.02.25 22:27 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AdminEditRow.jsx
 * $Id$
 */

import AdminEditArticleView from "../AdminEditArticleView/AdminEditArticleView";
import AdminEditView from "../AdminEditView/AdminEditView";
import IFrame from "../IFrame/IFrame";
import {loadCssListIframe1} from "../../lib/CssListIframe1";
import {setCalendarDate} from "../../redux/actions";

import React, {Component} from 'react';
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router";

const isArticle = object => object === 'article';
const isNewsEntry = object => object === 'news-entry';
const isNewsLinks = object => object === 'news-links';

export class AdminEditRow extends Component {

    constructor(props) {
        super(props);
        console.log("id=" + props.id)
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
            <AdminEditArticleView/>
        );
        if (isNewsEntry(object)) return (
            <AdminEditView/>
        );
        if (isNewsLinks(object)) return (
            <AdminEditView/>
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
        console.log('AdminEditRow.render');
        console.log(this.props);
        const object = this.getObject();

        return (
            <div className="my-row">
                <div className="my-side">
                    <h1>Home</h1>
                    <h2>About Me</h2>
                    <h5>Photo of me:</h5>
                    <div className="fakeimg">Image</div>
                    <p>Some text about me in culpa qui officia deserunt mollit anim..</p>
                    <h3>More Text</h3>
                    <p>Lorem ipsum dolor sit ame.</p>
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
    currentUser: state.currentUser,
    currentDate: state.currentDate
})

const mapDispatchToProps = dispatch => ({
    handleCalendarDate: value => dispatch(setCalendarDate(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(AdminEditRow);
