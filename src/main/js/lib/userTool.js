/*
 * This file was last modified at 2021.03.07 12:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * userTool.js
 * $Id$
 */

export const isAnonymity = (props) =>  {
    return JSON.stringify(props.user['currentUser']) === '{}';
}

export const isAdmin = (props) =>  {
    return JSON.stringify(props.user['currentUser']) === '\"Stalker\"';
}
