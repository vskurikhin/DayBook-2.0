/*
 * This file was last modified at 2021.03.01 20:59 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CreateRow.jsx
 * $Id$
 */

import CreateTabView from "./CreateTabView";
import IFrame from "../IFrame/IFrame";
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
                        <CreateTabView/>
                    </IFrame>
                </div>
            </div>
        )
    }
}

