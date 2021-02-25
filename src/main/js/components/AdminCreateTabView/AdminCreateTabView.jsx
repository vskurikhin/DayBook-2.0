/*
 * This file was last modified at 2021.02.25 22:27 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AdminCreateTabView.jsx
 * $Id$
 */

import {adminCreateNewsEntry} from "../../redux/actions";
import AdminCreateArticleView from "../AdminCreateArticleView/AdminCreateArticleView";
import AdminCreateNewsEntryView from "../AdminCreateNewsEntryView/AdminCreateNewsEntryView";

import React, {Component} from 'react';
import {TabView, TabPanel} from 'primereact/tabview';
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";

class AdminCreateTabView extends Component {

    constructor(props) {
        super(props);
    }

    render() {

        return (
            <div className="card">
                <h5>Default</h5>
                <TabView>
                    <TabPanel header="Create News Entry">
                        <AdminCreateNewsEntryView/>
                    </TabPanel>
                    <TabPanel header="Create Article">
                        <AdminCreateArticleView/>
                    </TabPanel>
                    <TabPanel header="Header III">
                        <p>At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati
                            cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio.
                            Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus.</p>
                    </TabPanel>
                </TabView>
            </div>
        );
    }
}

const mapStateToProps = state => ({
    ...state.currentUser,
    ...state.currentDate
})

const mapDispatchToProps = dispatch => ({
    adminCreateNewsEntry: value => dispatch(adminCreateNewsEntry(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(AdminCreateTabView);
