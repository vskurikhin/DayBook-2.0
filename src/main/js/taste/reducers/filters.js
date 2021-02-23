/*
 * This file was last modified at 2021.02.23 11:02 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * filters.js
 * $Id$
 */

const filtersReducerDefaultState = {
    text: '',
    sortBy: '',
    startYear: undefined,
    endYear: undefined
};

export default (state = filtersReducerDefaultState, action) => {
    switch (action.type) {
        case 'FILTER_TEXT':
            return {
                ...state,
                text: action.text
            };
        case 'START_YEAR':
            return {
                ...state,
                startYear: action.startYear
            };
        case 'END_YEAR':
            return {
                ...state,
                endYear: action.endYear
            };
        case 'SORT_BY':
            return {
                ...state,
                sortBy: action.sortType
            };
        case 'CLEAR':
            return {
                ...state,
                text: action.defaultFilter.text,
                sortBy: action.defaultFilter.sortBy,
                startYear: action.defaultFilter.startYear,
                endYear: action.defaultFilter.endYear
            };
        default:
            return state;
    }
}