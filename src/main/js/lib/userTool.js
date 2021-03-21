/*
 * This file was last modified at 2021.03.21 17:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * userTool.js
 * $Id$
 */

export const isAnonymity = (props) =>  {
    const {currentUser} = props.user;
    return JSON.stringify(currentUser) === '{}';
}

export const isAdmin = (props) =>  {
    const {currentUser} = props.user;
    return JSON.stringify(currentUser) === '\"Stalker\"';
}
