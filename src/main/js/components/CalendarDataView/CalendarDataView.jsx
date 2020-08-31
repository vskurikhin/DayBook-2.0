/*
 * This file was last modified at 2020.08.31 15:48 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CalendarDataView.jsx
 * $Id$
 */

import 'primeicons/primeicons.css';
import 'primereact/resources/themes/nova-light/theme.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';
import React, {Component, useEffect, useState} from 'react';
import { DataView } from 'primereact/dataview';
import { Panel } from "primereact/panel";

import { CarService } from '../../service/CarService';

const CalendarDataView = (date) => {
    const rows = 6;
    const [cars, setCars] = useState([]);
    const [totalRecords, setTotalRecords] = useState(0);
    const [loading, setLoading] = useState(true);
    const [first, setFirst] = useState(0);
    const carservice = new CarService();

    useEffect(() => {
        setTimeout(() => {
            carservice.getCarsFirstPage(0, 0, rows).then(function (resItems) {
                console.log("resItems", JSON.stringify(resItems));
                setTotalRecords(resItems.data.headers.resultCount);
                setCars(resItems.data.data);
            }).catch(function (error) {
                console.log(error);
            });
            setFirst(0);
            setLoading(false);
        }, 1000);
    }, []); // eslint-disable-line react-hooks/exhaustive-deps

    const renderGridItem = (car) => {
        return (
            <div style={{ padding: '.5em' }} className="p-col-12 p-md-4">
                <Panel header={car.vin} style={{ textAlign: 'left' }}>
                    <div className="car-detail">
                        <p align = "justify">
                            <img className="leftimg"
                                 src={`showcase/demo/images/car/${car.brand}.png`}
                                 srcSet="https://www.primefaces.org/wp-content/uploads/2020/05/placeholder.png"
                                 alt={car.brand}
                                 width="32"
                                 height="32"
                            />{car.color}
                        </p>
                    </div>
                    <div className="right">{car.year}</div>
                </Panel>
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
