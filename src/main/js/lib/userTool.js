/*
 * This file was last modified at 2021.02.03 18:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * userTool.js
 * $Id$
 */

export const isAnonymity = (props) =>  {
    return JSON.stringify(props.currentUser['currentUser']) === '{}';
}

export const isAdmin = (props) =>  {
    return JSON.stringify(props.currentUser['currentUser']) === '\"admin\"';
}
