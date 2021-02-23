/*
 * This file was last modified at 2021.02.23 11:02 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * BookFilters.jsx
 * $Id$
 */

import React from 'react';
import { connect } from 'react-redux';
import { filterText, startYear, endYear, sortBy } from '../actions/filters';

class BookFilters extends React.Component {
    constructor(props) {
        super(props);
        this.filterYear = this.filterYear.bind(this);
    }

    filterYear() {
        let start = (+this.startYear.value) !== 0 ? (+this.startYear.value) : undefined;
        let end = (+this.endYear.value) !== 0 ? (+this.endYear.value) : undefined;
        this.props.dispatch(startYear(start));
        this.props.dispatch(endYear(end));
    }

    render() {
        return (
            <div style={{ marginBottom: 15 }}>
                <input type='text' placeholder='search'
                       value={this.props.filters.text}
                       onChange={(e) => {
                           this.props.dispatch(filterText(e.target.value));
                       }}></input>

                sorted By:
                <select
                    value={this.props.filters.sortBy}
                    onChange={(e) => {
                        this.props.dispatch(sortBy(e.target.value));
                    }}>
                    <option value='none'>---</option>
                    <option value='title'>Title</option>
                    <option value='published'>Published</option>
                </select>
                <br /><br />

                <input type='number' placeholder='startYear' style={{ width: 80 }}
                       ref={el => this.startYear = el}></input>
                <input type='number' placeholder='endYear' style={{ width: 80 }}
                       ref={el => this.endYear = el}></input>

                <button onClick={this.filterYear}>-></button>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        filters: state.filters
    }
}

export default connect(mapStateToProps)(BookFilters);
