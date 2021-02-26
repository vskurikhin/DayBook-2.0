/*
 * This file was last modified at 2021.02.27 00:06 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * userTool.js
 * $Id$
 */

export const isAnonymity = (props) =>  {
    return JSON.stringify(props.user['currentUser']) === '{}';
}

export const isAdmin = (props) =>  {
    return JSON.stringify(props.user['currentUser']) === '\"admin\"';
}
