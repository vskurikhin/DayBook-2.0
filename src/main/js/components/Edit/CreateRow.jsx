/*
 * This file was last modified at 2021.03.02 23:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CreateRow.jsx
 * $Id$
 */

import CreateTabView from "./CreateTabView";
import IFrame from "../IFrame/IFrame";
import Side from '../Side/Side';
import {loadCssListIframe1} from "../../lib/CssListIframe1";

import React, {Component} from 'react';

export default class CreateRow extends Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
        loadCssListIframe1()
    }

    render() {
        const divStyle = {
            borderStyle: 'none',
            width: '100%',
            height: '100%'
        };

        return (
            <div className="my-row">
                <Side/>
                <div className="my-main" id='main'>
                    <IFrame style={divStyle} name='iframe1' id='iframe1'>
                        <CreateTabView/>
                    </IFrame>
                </div>
            </div>
        )
    }
}

