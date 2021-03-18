/*
 * This file was last modified at 2021.03.09 22:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RenderImg.jsx
 * $Id$
 */

import {WINDOW_WIDTH_LIMIT} from "./RootDataViewLazy";
import {isArticle, isNewsEntry, isNewsLinks} from "../../lib/is";

import React from 'react'

export const renderImg = props => {

    const renderImgArticle = record => (
        <img alt={record['articleTitle']}
             className="my-left-top"
             src="/raw-svg/file.svg"
             srcSet="/raw-svg/file.svg"
             height="64"
             width="64"
        />
    )

    const renderImgNewsEntry = record => (
        <img alt={record['newsEntryTitle']}
             className="my-left-top"
             src="/raw-svg/pause.svg"
             srcSet="/raw-svg/pause.svg"
             height="96"
             width="64"
        />
    )

    const renderImgNewsLinks = record => (
        <img alt={record['newsLinksTitle']}
             className="my-left-top"
             src="/raw-svg/link.svg"
             srcSet="/raw-svg/link.svg"
             height="72"
             width="64"
        />
    )

    if (props.windowWidth < WINDOW_WIDTH_LIMIT.BIG) return null;
    const {type, ...record} = props.value;
    if (isArticle(type))
        return renderImgArticle(record);
    if (isNewsEntry(type))
        return renderImgNewsEntry(record);
    if (isNewsLinks(type))
        return renderImgNewsLinks(record);
    return (
        <div style={{padding: '.5em', border: 0}}>image for {props.id}</div>
    );
}

export default renderImg;
