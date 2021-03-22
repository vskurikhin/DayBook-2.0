/*
 * This file was last modified at 2021.03.22 19:30 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RenderItem.jsx
 * $Id$
 */

import 'primeicons/primeicons.css';
import 'primereact/resources/primereact.min.css';
import 'primeflex/primeflex.css';
import './Button.css';

import RenderTBody from "./RenderTBody";
import isEmpty from "../../lib/isEmpty";
import kebabize from "../../lib/kebabize";
import renderTitle from "./renderTitle";
import {isAdmin} from "../../lib/userTool";

import * as React from "react";
import {useState} from "react";
import {useWindowResize} from "beautiful-react-hooks";

export const renderItem = props => {

    const [windowWidth, setWindowWidth] = useState(window.innerWidth);

    // noinspection JSAnnotator
    useWindowResize((event: React.SyntheticEvent) => {
        if (Math.abs(windowWidth - window.innerWidth) > 10)
            setWindowWidth(window.innerWidth);
    });

    const clickHandler = (event, id, type) => {
        event.stopPropagation();
        event.persist();
        setTimeout(() => {
            props.cm.current.hide(event);
            if (event.altKey || event.ctrlKey || event.metaKey) {
                if (isAdmin(props)) {
                    history.push("/edit/" + kebabize(type) + '/' + id);
                }
            }
        }, 100);
    }

    const {renderContextMenu, value} = props;
    const {id, publicTime, tags, ...record} = value;

    if (isEmpty(value)) return (
        <div style={{border: 0}} />
    );

    return (
        <div className="p-col-12 p-md-4"
             onClick={(e) => clickHandler(e, id, record.type)}
             style={{padding: '.5em', border: 0}}>
            <div id={id}
                 className="p-panel p-component"
                 style={{textAlign: 'left'}}
                 onContextMenu={(e) => renderContextMenu(e, record.type, id)}>
                <div className="p-panel-header">
                        <span aria-label={id} className="p-panel-title"
                        >{renderTitle(id, record)}</span>
                    <div className="p-panel-icons"/>
                </div>
                <div aria-labelledby={id}
                     aria-hidden="false"
                     className="p-toggleable-content"
                     role="region">
                    <div className="p-panel-content">
                        <table aria-haspopup className="news-entry">
                            <RenderTBody
                                id={id}
                                publicTime={publicTime}
                                tags={tags}
                                value={record}
                                windowWidth={windowWidth}
                                {...props}
                            />
                        </table>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default renderItem;