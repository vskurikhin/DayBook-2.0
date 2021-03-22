/*
 * This file was last modified at 2021.03.22 19:30 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RenderAfterContent.jsx
 * $Id$
 */


import {isArticle, isNewsLinks} from "../../lib/is";

import * as React from "react";

export const renderAfterContent = props => {

    const renderSummaryIncludeAnchor = (id, record) => (
        <ul>
            <li><a href={record['articleInclude']}>{record['articleAnchor']}</a></li>
        </ul>
    )

    const renderLinkNewsLinks = (id, record) => (
        <ul>
            <li><a href={record.links}>{record.links}</a></li>
        </ul>
    )

    const {type, ...record} = props.value;

    if (isArticle(type))
        return renderSummaryIncludeAnchor(props.id, record);
    if (isNewsLinks(type))
        return renderLinkNewsLinks(props.id, record);

    return (
        <div aria-labelledby={"after_content_for_" + props.id}/>
    );
}

export default renderAfterContent;
