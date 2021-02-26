/*
 * This file was last modified at 2021.02.26 10:44 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CalendarDataView.jsx
 * $Id$
 */

import 'primeicons/primeicons.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';

import {AllRecordService} from '../../service/AllRecordService';

import React, {useEffect, useState} from 'react';
import {DataView} from 'primereact/dataview';

const CalendarDataView = (date) => {
    const rows = 6;
    const [cars, setCars] = useState([]);
    const [totalRecords, setTotalRecords] = useState(0);
    const [loading, setLoading] = useState(true);
    const [first, setFirst] = useState(0);

    const renderGridItem = (car) => {
        return (
            <div className="p-col-12"  key={record.id}>
                <div className="car-details">
                    <div>
                        <div className="p-grid">
                            <div className="p-col-12">type: <b>{record.type}</b></div>
                            <div className="p-col-12">updateTime: <b>{record.updateTime}</b></div>
                            <div className="p-col-12">
                                <img src="/raw-svg/sitemap.svg"
                                     srcSet="/raw-svg/sitemap.svg"
                                     alt={record.visible}
                                />Brand: <b>{record.visible}</b>
                            </div>
                            <div className="p-col-12">tags: <b>{record.tags}</b></div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    const itemTemplate = (car) => {
        return renderGridItem(car);
    }

    return (
        <div className="dataview-demo">
            <DataView value={cars} itemTemplate={itemTemplate}
                      paginator paginatorPosition={'both'} rows={rows} totalRecords={totalRecords} />
        </div>
    );
}

export default CalendarDataView;
