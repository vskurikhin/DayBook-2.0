/*
 * This file was last modified at 2021.02.03 18:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * formatDate.js
 * $Id$
 */

export const pad = (n) => {
    return n < 10 ? '0' + n : n
}

export const formatDate = (date) => {
    return date.year + '-' + pad(date.month) + '-' + pad(date.date);
}


export const logDate = (message, date) => {
    console.log(message + formatDate(date));
}
