/*
 * This file was last modified at 2021.02.23 11:02 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AddBook.jsx
 * $Id$
 */

import React from 'react';
import BookForm from './BookForm';
import { connect } from 'react-redux';
import { addBook } from '../actions/books';

const AddBook = (props) => (
    <div>
        <h3>Set Book information:</h3>
        <BookForm
            onSubmitBook={(book) => {
                props.dispatch(addBook(book));
                props.history.push('/');
            }}
        />
    </div>
);

export default connect()(AddBook);
