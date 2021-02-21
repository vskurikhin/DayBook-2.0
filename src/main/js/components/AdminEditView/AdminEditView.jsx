/*
 * This file was last modified at 2021.02.21 20:37 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AdminEditView.jsx
 * $Id$
 */

import {setCalendarDate} from "../../redux/actions";

import {compose} from "redux";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";

const AdminEditView = (props) => {

    return (
        <div className="dataview-demo">
        </div>
    );
}

const mapStateToProps = state => ({
    currentUser: state.currentUser,
    currentDate: state.currentDate
})

const mapDispatchToProps = dispatch => ({
    handleCalendarDate: value => dispatch(setCalendarDate(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(AdminEditView);
