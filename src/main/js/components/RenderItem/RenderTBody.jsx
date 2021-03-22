/*
 * This file was last modified at 2021.03.22 19:30 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RenderTBody.jsx
 * $Id$
 */

import RenderAfterContent from "./RenderAfterContent";
import RenderImg from "./RenderImg";
import RenderPublicTime from "./RenderPublicTime";
import RenderTags, {convertToItems} from "./RenderTags";
import {isArticle, isNewsEntry, isNewsLinks} from "../../lib/is";

import React from "react";

const renderTBody = props => {

    const renderContent = (id, value) => {
        const {type, ...record} = value;
        if (isArticle(type))
            return record['articleSummary'];
        if (isNewsEntry(type))
            return record['newsEntryContent'];
        if (isNewsLinks(type))
            return null;
        return "content for " + id;
    }

    const renderUserName = (id, value) => {
        const {type, ...record} = value;
        if (record.userName === null)
            return "username for " + id;
        return record.userName;
    }

    return (
        <tbody>
        <tr>
            <td className="my-news-entry-first-th" rowSpan="3">
                <RenderImg
                    id={props.id}
                    value={props.value}
                    windowWidth={props.windowWidth}
                    {...props}
                />
            </td>
            <td className="valueField my-news-entry-second-th"
                colSpan="2"
                rowSpan="2">
                <div align="justify" dangerouslySetInnerHTML={{
                    __html: renderContent(props.id, props.value)
                }}
                />
                <RenderAfterContent id={props.id} value={props.value}/>
            </td>
        </tr>
        <tr>
            <td/>
        </tr>
        <tr>
            <td>{renderUserName(props.id, props.value)}</td>
            <td className="my-news-entry-date-th">
                <RenderPublicTime
                    publicTime={props.publicTime}
                    windowWidth={props.windowWidth}
                    {...props}
                />
            </td>
        </tr>
        <tr>
            <td/>
            <td className="my-news-entry-tags-th" colSpan="2">
                <RenderTags items={convertToItems(props.tags)}/>
            </td>
        </tr>
        </tbody>
    )
}

export default renderTBody;
