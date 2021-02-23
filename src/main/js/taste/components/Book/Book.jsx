/*
 * This file was last modified at 2021.02.23 11:02 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Book.jsx
 * $Id$
 */

import React from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import { removeBook } from '../actions/books';

const Book = ({ id, title, description, author, published, dispatch }) => (
    <div>
        <Link to={`/book/${id}`}>
            <h4>{title} ({published})</h4>
        </Link>
        <p>Author: {author}</p>
        {description && <p>{description}</p>}
        <button onClick={() => {
            dispatch(removeBook({ id }));
        }}>Remove</button>
    </div>
);

export default connect()(Book);
