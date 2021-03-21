/*
 * This file was last modified at 2021.03.21 13:40 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryView.jsx
 * $Id$
 */

import './DropdownDemo.css';
import 'primeicons/primeicons.css';
import 'primereact/resources/primereact.min.css';
import 'primeflex/primeflex.css';

import EditHandlers from "../EditHandlers";

import React from 'react';
import {AutoComplete} from 'primereact/autocomplete';
import {Button} from 'primereact/button';
import {Calendar} from "primereact/calendar";
import {Dropdown} from 'primereact/dropdown';
import {InputTextarea} from 'primereact/inputtextarea';
import {InputText} from 'primereact/inputtext';
import {Text} from "@eo-locale/react";

export default class NewsEntryView extends EditHandlers {

    constructor(props) {
        super(props);
    }

    newsEntryView = () => (
        <div className="dataview-demo">
            <form onSubmit={this.handleSubmit}>
                <div className="card">
                    <div className="my-divTable">
                        <div className="my-divTableBody">
                            <div className="my-divTableRow">
                                <div className="my-divTableCellLeft">&nbsp;</div>
                                <div className="my-divTableCell">
                                    <label className="my-label"><b><Text id='News_Group'/>:</b></label><br/>
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
                                    <span className="p-float-label" style={{minWith: '22.4em'}}>
                                        <AutoComplete
                                            id="pr_id_3"
                                            completeMethod={this.searchTagLabels}
                                            field="label"
                                            multiple onChange={this.onTagLabelsChange}
                                            suggestions={this.state.filteredTagLabels}
                                            value={this.state.selectedTags}
                                        />
                                        <label htmlFor="time24"><b><Text id='Tags'/>:</b></label>
                                    </span>
                                </div>
                                <div className="my-divTableCellRight">&nbsp;</div>
                            </div>

                            <div className="my-divTableRow">
                                <div className="my-divTableCellLeft">&nbsp;</div>
                                <div className="my-divTableCell">
                                        <span className="p-float-label">
                                             <Calendar
                                                 id="time24"
                                                 dateFormat="yy-mm-dd"
                                                 hideOnDateTimeSelect={true}
                                                 inputClassName="inputTest"
                                                 onChange={this.onPublicTimeChange}
                                                 panelClassName="panelTest"
                                                 readOnlyInput
                                                 showOnFocus={true}
                                                 showSeconds
                                                 showTime
                                                 locale={this.props.locale.language}
                                                 value={this.state.data.publicTime}
                                             />
                                            <label htmlFor="time24"><b><Text id='Time'/>:</b></label>
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
                                            <label htmlFor="title"><b><Text id='Title'/>:</b></label>
                                        </span>
                                </div>
                                <div className="my-divTableCellRight">&nbsp;</div>
                            </div>

                            <div className="my-divTableRow">
                                <div className="my-divTableCellLeft">&nbsp;</div>
                                <div className="my-divTableCell">
                                    <label className="my-label"><b><Text id='Content'/>:</b></label><br/>
                                    <InputTextarea
                                        className="my-p-inputtext"
                                        autoResize
                                        cols={30}
                                        name='content'
                                        onChange={this.onChangeDefault}
                                        rows={5}
                                        style={{with: '100%'}}
                                        value={this.state.data.content}
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
