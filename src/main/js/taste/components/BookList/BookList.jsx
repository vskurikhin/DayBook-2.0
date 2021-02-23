/*
 * This file was last modified at 2021.02.23 11:21 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * BookList.jsx
 * $Id$
 */

import Book from '../Book/Book';
import getVisibleBooks from '../../selectors/books';

import React from 'react';
import {connect} from 'react-redux';

const BookList = (props) => (
    <div>
        Book List:
        <ul>
            {props.books.map(book => {
                return (
                    <li key={book.id}>
                        <Book {...book} />
                    </li>
                );
            })}
        </ul>
    </div>
);

const mapStateToProps = (state) => {
    return {
        books: getVisibleBooks(state.books, state.filters)
    };
}

export default connect(mapStateToProps)(BookList);
