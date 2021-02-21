/*
 * This file was last modified at 2021.02.21 20:37 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AdminCreateView.jsx
 * $Id$
 */

import {adminCreateNewsEntry} from '../../redux/actions';

import React, {Component} from 'react';
import {Button} from 'primereact/button';
import {InputMask} from 'primereact/inputmask';
import {InputTextarea} from 'primereact/inputtextarea';
import {InputText} from 'primereact/inputtext';
import {Redirect} from "react-router";
import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";

class AdminCreateView extends Component {
    state = {
        newsGroupId: "00000000-0000-0000-0000-000000000001",
        title: "",
        content: "",
        redirectToReferrer: false
    }

    constructor(props) {
        super(props);
    }

    handleChange = event => {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    handleSubmit = event => {
        event.preventDefault()
        this.props.adminCreateNewsEntry(this.state)
        this.setState({redirectToReferrer: true})
    }

    render() {
        const redirectToReferrer = this.state.redirectToReferrer;
        if (redirectToReferrer === true) {
            return <Redirect to="/home" />
        }
        return (
            <div className="dataview-demo">
                <form onSubmit={this.handleSubmit}>
                    <div className="card">
                        <div className="my-divTable">
                            <div className="my-divTableBody">
                                <div className="my-divTableRow">
                                    <div className="my-divTableCellLeft">&nbsp;</div>
                                    <div className="my-divTableCell">
                                        <h6 className="my-h5">News group Id:</h6>
                                        <InputMask
                                            className="my-p-inputtext"
                                            id="inputmask"
                                            mask="********-****-****-****-************"
                                            name='newsGroupId'
                                            onChange={this.handleChange}
                                            slotChar="00000000-0000-0000-0000-000000000001"
                                            value={this.state.newsGroupId}
                                        />
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
                                                onChange={this.handleChange}
                                                type="text"
                                                value={this.state.title}
                                            />
                                            <label htmlFor="title"><b>title</b></label>
                                        </span>
                                    </div>
                                    <div className="my-divTableCellRight">&nbsp;</div>
                                </div>
                                <div className="my-divTableRow">
                                    <div className="my-divTableCellLeft">&nbsp;</div>
                                    <div className="my-divTableCell">
                                        <h5 className="my-h5">Content:</h5>
                                        <InputTextarea
                                            className="my-p-inputtext"
                                            autoResize
                                            cols={30}
                                            name='content'
                                            onChange={this.handleChange}
                                            rows={5}
                                            value={this.state.content}
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
}

const mapStateToProps = state => ({
    currentUser: state.currentUser,
    currentDate: state.currentDate
})


const mapDispatchToProps = dispatch => ({
    adminCreateNewsEntry: value => dispatch(adminCreateNewsEntry(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(AdminCreateView);
