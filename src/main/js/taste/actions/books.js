/*
 * This file was last modified at 2021.02.23 11:02 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * books.js
 * $Id$
 */

import uuid from 'uuid';

export const addBook = ({
    title = '',
    description = '',
    author = '',
    published = 0
} = {}) => ({
    type: 'ADD_BOOK',
    book: {
        id: uuid(),
        title,
        description,
        author,
        published
    }
});

export const removeBook = ({ id } = {}) => ({
    type: 'REMOVE_BOOK',
    id
});

export const editBook = (id, updates) => ({
    type: 'EDIT_BOOK',
    id,
    updates
});
