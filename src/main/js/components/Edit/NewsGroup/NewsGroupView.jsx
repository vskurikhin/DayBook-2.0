/*
 * This file was last modified at 2021.03.02 17:18 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupView.jsx
 * $Id$
 */

import EditHandlers from "../EditHandlers";
import {InputText} from "primereact/inputtext";
import {Button} from "primereact/button";
import React from "react";

export default class NewsGroupView extends EditHandlers {

    constructor(props) {
        super(props);
    }

    newsGroupView = () => (
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
                                                id="groupName"
                                                name='groupName'
                                                onChange={this.onChangeDefault}
                                                style={{with: '100%'}}
                                                type="text"
                                                value={this.state.data.groupName}
                                            />
                                            <label htmlFor="groupName"><b>Group name:</b></label>
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