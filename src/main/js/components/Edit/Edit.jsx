/*
 * This file was last modified at 2021.03.01 20:59 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Edit.jsx
 * $Id$
 */

import EditRow from "./EditRow";
import Header from "../Header/Header";
import NavigationBar from "../NavigationBar/NavigationBar";

import React, {Component} from 'react';

export default class Edit extends Component {

    render () {
        return (
            <div>
                <Header />
                <NavigationBar />
                <EditRow />
                <div className="my-footer">
                </div>
            </div>
        )
    }
}
