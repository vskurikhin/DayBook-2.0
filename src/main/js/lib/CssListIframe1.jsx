/*
 * This file was last modified at 2021.02.01 23:11 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CssListIframe1.jsx
 * $Id$
 */

const FILES = [
    '/css/primeicons.css',
    '/css/primereact.min.css',
    '/css/theme.css',
    '/css/index.css'
]

export const loadCssListIframe1 = () => {
    var iframe1 = document.getElementById('iframe1'); // getElementsByTagName("iframe")[0].contentWindow;
    var head = iframe1.contentWindow.document.getElementsByTagName("head")[0];
    FILES.forEach(function (item, i, arr) {
        var cssLink = document.createElement("link", {href: item, rel: 'stylesheet', type: 'text/css'});
        cssLink.href = item;
        cssLink.rel = "stylesheet";
        cssLink.type = "text/css";
        head.appendChild(cssLink);
    });
}
