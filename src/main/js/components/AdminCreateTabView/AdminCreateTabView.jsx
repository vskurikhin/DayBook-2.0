/*
 * This file was last modified at 2021.02.25 16:07 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AdminCreateTabView.jsx
 * $Id$
 */

import {adminCreateNewsEntry} from "../../redux/actions";
import AdminCreateView from "../AdminCreateView/AdminCreateView";

import React, {Component, useState} from 'react';
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
                    <TabPanel header="Header I">
                        <AdminCreateView/>
                    </TabPanel>
                    <TabPanel header="Header II">
                        <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi
                            architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione
                            voluptatem sequi nesciunt. Consectetur, adipisci velit, sed quia non numquam eius modi.</p>
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
