/*
 * This file was last modified at 2021.03.21 13:40 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagLabelView.jsx
 * $Id$
 */

import 'primeicons/primeicons.css';
import 'primereact/resources/primereact.min.css';
import 'primeflex/primeflex.css';

import EditHandlers from "../EditHandlers";

import React from "react";
import {Button} from "primereact/button";
import {InputText} from "primereact/inputtext";

export default class TagLabelView extends EditHandlers {

    constructor(props) {
        super(props);
    }

    tagLabelView = () => (
        <div className="dataview-demo">
            <form onSubmit={this.handleSubmit}>
                <div className="card">
                    <div className="my-divTable">
                        <div className="my-divTableBody">

                            <div className="my-divTableRow">
                                <div className="my-divTableCellLeft">&nbsp;</div>
                                <div className="my-divTableCell">
                                        <span className="p-float-label">
                                            <InputText
                                                className="my-p-inputtext"
                                                id="label"
                                                name='label'
                                                onChange={this.onChangeDefault}
                                                style={{with: '100%'}}
                                                type="text"
                                                value={this.state.data.label}
                                            />
                                            <label htmlFor="label"><b>Tag Label:</b></label>
                                        </span>
                                </div>
                                <div className="my-divTableCellRight">&nbsp;</div>
                            </div>

                            <div className="my-divTableRow">
                                <div className="my-divTableCellLeft">&nbsp;</div>
                                <div className="my-divTableCell">
                                    <Button
                                        className="my-p-button"
                                        icon="pi pi-check"
                                        iconPos="right"
                                        label="Submit"
                                    />
                                </div>
                                <div className="my-divTableCellRight">&nbsp;</div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    );
}