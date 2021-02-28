/*
 * This file was last modified at 2021.02.28 23:25 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Create.jsx
 * $Id$
 */

import CreateRow from "./CreateRow/CreateRow";
import Header from "../Header/Header";
import NavigationBar from "../NavigationBar/NavigationBar";

import React, {Component} from 'react';
import {connect} from "react-redux";

class Create extends Component {

    render () {
        return (
            <div>
                <Header />
                <NavigationBar />
                <CreateRow />
                <div className="my-footer">
                </div>
            </div>
        )
    }
}

const mapStateToProps = state => ({
    user: state.currentUser,
})

export default connect(mapStateToProps)(Create);
