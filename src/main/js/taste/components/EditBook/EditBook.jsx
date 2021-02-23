/*
 * This file was last modified at 2021.02.23 11:21 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * EditBook.jsx
 * $Id$
 */

import {connect} from 'react-redux';
import {editBook} from '../../actions/books';

import React from 'react';
import BookForm from '../BookForm/BookForm';

const EditBook = (props) => (
    <div className='container__box'>
        <BookForm
            book={props.book}
            onSubmitBook={(book) => {
                props.dispatch(editBook(props.book.id, book));
                props.history.push('/');
            }}
        />
    </div>
);

const mapStateToProps = (state, props) => {
    return {
        book: state.books.find((book) =>
            book.id === props.match.params.id)
    };
};

export default connect(mapStateToProps)(EditBook);
