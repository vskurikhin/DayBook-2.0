/*
 * This file was last modified at 2021.02.23 11:21 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Header.jsx
 * $Id$
 */

import React from 'react';
import {NavLink} from 'react-router-dom';

const Header = () => (
    <header>
        <h2>Java Sample Approach</h2>
        <h4>Book Mangement Application</h4>
        <div className='header__nav'>
            <NavLink to='/' activeClassName='activeNav' exact={true}>Dashboard</NavLink>
            <NavLink to='/add' activeClassName='activeNav'>Add Book</NavLink>
            <NavLink to='/help' activeClassName='activeNav'>Help</NavLink>
        </div>
    </header>
);

export default Header;
