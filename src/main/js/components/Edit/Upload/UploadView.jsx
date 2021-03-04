/*
 * This file was last modified at 2021.03.04 13:41 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UploadView.jsx
 * $Id$
 */

import EditHandlers from "../EditHandlers";

import React from "react";
import {Button} from "primereact/button";
import {FileUpload} from 'primereact/fileupload';
import {Toast} from 'primereact/toast';

export default class UploadView extends EditHandlers {

    constructor(props) {
        super(props);
    }

    uploadView = () => (
        <div className="dataview-demo">
            <form onSubmit={this.handleSubmit}>
                <div className="card">
                    <div className="my-divTable">
                        <div className="my-divTableBody">

                            <div className="my-divTableRow">
                                <div className="my-divTableCellLeft">&nbsp;</div>
                                <div className="my-divTableCell">
                                    <Toast ref={this.state.toast}/>
                                </div>
                                <div className="my-divTableCellRight">&nbsp;</div>
                            </div>

                            <div className="my-divTableRow">
                                <div className="my-divTableCellLeft">&nbsp;</div>
                                <div className="my-divTableCell">
                                    <h5>Basic</h5>
                                    <FileUpload
                                        accept="image/*"
                                        maxFileSize={10000000}
                                        mode="basic"
                                        name="file"
                                        onBeforeSend={this.onBeforeSend}
                                        url="/multipart-file/upload-mono"
                                    />
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