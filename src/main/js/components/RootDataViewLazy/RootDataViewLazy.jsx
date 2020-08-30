import 'primeicons/primeicons.css';
import 'primereact/resources/themes/nova-light/theme.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';

import React, { useState, useEffect } from 'react';
import { DataView, DataViewLayoutOptions } from 'primereact/dataview';
import { Panel } from 'primereact/panel';
import { CarService } from '../../service/CarService';

const RootDataViewLazy = () => {
    const [cars, setCars] = useState([]);
    const [layout, setLayout] = useState('list');
    const [loading, setLoading] = useState(true);
    const [first, setFirst] = useState(0);
    const [totalRecords, setTotalRecords] = useState(0);
    const rows = 6;
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

    const onPage = (event) => {
        setLoading(true);
        console.log("event", JSON.stringify(event));

        //imitate delay of a backend call
        setTimeout(() => {
            const startIndex = event.first;
            carservice.getCarsLazy(event.originalEvent).then(function (resItems) {
                console.log("resItems", JSON.stringify(resItems));
                setTotalRecords(resItems.data.headers.resultCount);
                setCars(resItems.data.data);
            }).catch(function (error) {
                console.log(error);
            });
            setFirst(startIndex);
            setLoading(false);
        }, 1000);
    }

    const renderListItem = (car) => {
        return (
            <div className="p-col-12">
                <div className="car-details">
                    <div>

                        <div className="p-grid">
                            <div className="p-col-12">Vin: <b>{car.vin}</b></div>
                            <div className="p-col-12">Year: <b>{car.year}</b></div>
                            <div className="p-col-12"><img src={`showcase/demo/images/car/${car.brand}.png`} srcSet="https://www.primefaces.org/wp-content/uploads/2020/05/placeholder.png" alt={car.brand} />Brand: <b>{car.brand}</b></div>
                            <div className="p-col-12">Color: <b>{car.color}</b></div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    const renderGridItem = (car) => {
        return (
            <div style={{ padding: '.5em' }} className="p-col-12 p-md-4">
                <Panel header={car.vin} style={{ textAlign: 'left' }}>
                    <div className="car-detail">
                        <p align = "justify">
                            <img className="leftimg" src={`showcase/demo/images/car/${car.brand}.png`} srcSet="https://www.primefaces.org/wp-content/uploads/2020/05/placeholder.png" alt={car.brand} width="32" height="32" /> {car.color}
                        </p>
                    </div>
                    <div className="right">{car.year}</div>
                </Panel>
            </div>
        );
    }

    const itemTemplate = (car, layout) => {
        if (!car) {
            return;
        }

        if (layout === 'grid')
            return renderListItem(car);
        else if (layout === 'list')
            return renderGridItem(car);
    }

    const renderHeader = () => {
        let onOptionChange = (e) => {
            setLoading(true);

            setTimeout(() => {
                setLoading(false);
                setLayout(e.value);
            }, 1000);
        };

        return (
            <div style={{textAlign: 'left'}}>
                <DataViewLayoutOptions layout={layout} onChange={onOptionChange} />
            </div>
        );
    }

    // const header = renderHeader();

    return (
        <div className="dataview-demo">
            <DataView value={cars} layout={layout} itemTemplate={itemTemplate}
                      lazy paginator paginatorPosition={'both'} rows={rows} totalRecords={totalRecords}
                      first={first} onPage={onPage} loading={loading} />
        </div>
    );
}

export default RootDataViewLazy;

// const rootElement = document.getElementById("root");
// ReactDOM.render(<RootDataViewLazy />, rootElement);
