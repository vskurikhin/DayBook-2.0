/*
 * This file was last modified at 2021.02.23 11:21 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DashBoard.jsx
 * $Id$
 */

import BookList from '../BookList/BookList';
import BookFilters from '../BookFilters/BookFilters';

import React from 'react';

const DashBoard = () => (
    <div className='container__list'>
        <BookFilters />
        <BookList />
    </div>
);

export default DashBoard;
