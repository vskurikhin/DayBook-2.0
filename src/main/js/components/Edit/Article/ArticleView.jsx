/*
 * This file was last modified at 2021.03.01 23:42 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleView.jsx
 * $Id$
 */


import EditHandlers from "../EditHandlers";
import React from 'react';
import {AutoComplete} from "primereact/autocomplete";
import {Button} from "primereact/button";
import {Dropdown} from "primereact/dropdown";
import {InputTextarea} from "primereact/inputtextarea";
import {InputText} from "primereact/inputtext";

export default class ArticleView extends EditHandlers {

    constructor(props) {
        super(props);
    }

    articleView = () => (
        <div className="dataview-demo">
            <form onSubmit={this.handleSubmit}>
                <div className="card">
                    <div className="my-divTable">
                        <div className="my-divTableBody">
                            <div className="my-divTableRow">
                                <div className="my-divTableCellLeft">&nbsp;</div>
                                <div className="my-divTableCell">
                                    <label className="my-label"><b>News group Id:</b></label><br/>
                                    <Dropdown
                                        onChange={this.onNewsGroupChange}
                                        optionLabel="groupName"
                                        options={this.state.newsGroupNames}
                                        placeholder="Select a News group"
                                        value={this.state.selectedNewsGroup}
                                    />
                                </div>
                                <div className="my-divTableCellRight">&nbsp;</div>
                            </div>

                            <div className="my-divTableRow">
                                <div className="my-divTableCellLeft">&nbsp;</div>
                                <div className="my-divTableCell">
                                    <label className="my-label"><b>Tags:</b></label><br/>
                                    <span className="p-float-label">
                                            <AutoComplete
                                                completeMethod={this.searchTagLabels}
                                                field="label"
                                                multiple onChange={this.onTagLabelsChange}
                                                style={{with: '100%'}}
                                                panelStyle={{with: '100%'}}
                                                suggestions={this.state.filteredTagLabels}
                                                value={this.state.selectedTags}
                                            />
                                        </span>
                                </div>
                                <div className="my-divTableCellRight">&nbsp;</div>
                            </div>

                            <div className="my-divTableRow">
                                <div className="my-divTableCellLeft">&nbsp;</div>
                                <div className="my-divTableCell">
                                        <span className="p-float-label">
                                            <InputText
                                                className="my-p-inputtext"
                                                id="title"
                                                name='title'
                                                onChange={this.onChangeDefault}
                                                style={{with: '100%'}}
                                                type="text"
                                                value={this.state.data.title}
                                            />
                                            <label htmlFor="title"><b>Title:</b></label>
                                        </span>
                                </div>
                                <div className="my-divTableCellRight">&nbsp;</div>
                            </div>

                            <div className="my-divTableRow">
                                <div className="my-divTableCellLeft">&nbsp;</div>
                                <div className="my-divTableCell">
                                        <span className="p-float-label">
                                            <InputText
                                                className="my-p-inputtext"
                                                id="include"
                                                name='include'
                                                onChange={this.onChangeDefault}
                                                type="text"
                                                value={this.state.data.include}
                                            />
                                            <label htmlFor="include"><b>Include:</b></label>
                                        </span>
                                </div>
                                <div className="my-divTableCellRight">&nbsp;</div>
                            </div>

                            <div className="my-divTableRow">
                                <div className="my-divTableCellLeft">&nbsp;</div>
                                <div className="my-divTableCell">
                                        <span className="p-float-label">
                                            <InputText
                                                className="my-p-inputtext"
                                                id="anchor"
                                                name='anchor'
                                                onChange={this.onChangeDefault}
                                                type="text"
                                                value={this.state.data.anchor}
                                            />
                                            <label htmlFor="anchor"><b>Anchor:</b></label>
                                        </span>
                                </div>
                                <div className="my-divTableCellRight">&nbsp;</div>
                            </div>

                            <div className="my-divTableRow">
                                <div className="my-divTableCellLeft">&nbsp;</div>
                                <div className="my-divTableCell">
                                    <label className="my-label"><b>Summary:</b></label><br/>
                                    <InputTextarea
                                        className="my-p-inputtext"
                                        autoResize
                                        cols={30}
                                        name='summary'
                                        onChange={this.onChangeDefault}
                                        rows={5}
                                        value={this.state.data.summary}
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