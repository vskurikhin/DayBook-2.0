/*
 * This file was last modified at 2021.03.22 19:30 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RenderTags.jsx
 * $Id$
 */

import 'primeicons/primeicons.css';
import 'primereact/resources/primereact.min.css';
import 'primeflex/primeflex.css';

import * as React from "react";
import {Button} from "primereact/button";
import {map} from "underscore";

const renderTags = props => {

    return (
        <div>{map(props.items, ({index, value}) => (
            <Button
                style={{marginLeft: "2px"}}
                key={index}
                label={value}
                className="my-p-button-sm p-button-rounded p-button-secondary"
                disabled
            />
        ))}</div>
    )
}

export const convertToItems = tags => {

    const items = [];
    for (let object in tags) {
        // noinspection JSUnfilteredForInLoop
        let index = Object.keys(tags).indexOf(object);
        items.push({index: index, value: tags[index]});
    }

    return items;
}

export default renderTags;
