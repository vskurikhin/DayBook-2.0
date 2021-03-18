/*
 * This file was last modified at 2021.03.09 22:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RenderPublicTime.jsx
 * $Id$
 */

import * as React from "react";
import moment from "moment";
import {DateTime} from "@eo-locale/react";
import {WINDOW_WIDTH_LIMIT} from "../../config/consts";

const renderPublicTime = props => {

    if (props.windowWidth < WINDOW_WIDTH_LIMIT.SMALL) return (
        <DateTime
            language={props.locale.language}
            value={moment(props.publicTime)}
        />
    );

    return (
        <DateTime
            language={props.locale.language}
            value={moment(props.publicTime)}
            day="numeric"
            month={props.windowWidth > WINDOW_WIDTH_LIMIT.MIDDLE ? "long" : "short"}
            weekday={props.windowWidth > WINDOW_WIDTH_LIMIT.MIDDLE ? "long" : "short"}
            year="numeric"
        />
    );
}

export default renderPublicTime;
