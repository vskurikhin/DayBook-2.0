/*
 * This file was last modified at 2021.03.21 13:40 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * renderTitle.js
 * $Id$
 */

export const renderTitle = (id, value) => {
    const {type, ...record} = value;
    if (record.title === null)
        return "title for " + id;
    return record.title;
}

export default renderTitle;
