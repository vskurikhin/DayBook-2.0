/*
 * This file was last modified at 2021.02.23 11:02 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * books.js
 * $Id$
 */

const booksReducerDefaultState = [];

export default (state = booksReducerDefaultState, action) => {
    switch (action.type) {
        case 'ADD_BOOK':
            return [
                ...state,
                action.book
            ];
        case 'REMOVE_BOOK':
            return state.filter(({ id }) => id !== action.id);
        case 'EDIT_BOOK':
            return state.map((book) => {
                if (book.id === action.id) {
                    return {
                        ...book,
                        ...action.updates
                    };
                } else {
                    return book;
                }
            });
        default:
            return state;
    }
};