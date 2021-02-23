/*
 * This file was last modified at 2021.02.23 11:02 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DashBoard.jsx
 * $Id$
 */

import React from 'react';
import BookList from './BookList';
import BookFilter from './BookFilter';

const DashBoard = () => (
    <div className='container__list'>
        <BookFilter />
        <BookList />
    </div>
);

export default DashBoard;
