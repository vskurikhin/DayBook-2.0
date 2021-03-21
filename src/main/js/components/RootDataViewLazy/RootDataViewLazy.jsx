/*
 * This file was last modified at 2021.03.21 17:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RootDataViewLazy.jsx
 * $Id$
 */

import 'primeicons/primeicons.css';
import 'primereact/resources/primereact.min.css';
import 'primeflex/primeflex.css';
import './Button.css';

import RenderTBody from "../RenderTBody/RenderTBody";
import isEmpty from "../../lib/isEmpty";
import kebabize from "../../lib/kebabize";
import renderTitle from "../RenderTBody/renderTitle";
import {AllRecordService} from '../../service/AllRecordService';
import {NUMBER_OF_ELEMENTS, TIMEOUT} from "../../config/consts";
import {isAdmin} from '../../lib/userTool'
import {isArticle, isNewsEntry, isNewsLinks} from "../../lib/is";
import {setCalendarDate, setPage} from "../../redux/actions";

import * as React from "react";
import axios from "axios";
import {ContextMenu} from 'primereact/contextmenu';
import {DataView} from 'primereact/dataview';
import {compose} from "redux";
import {connect} from "react-redux";
import {useEffect, useRef, useState} from "react";
import {useHistory, withRouter} from 'react-router-dom';
import {useWindowResize} from "beautiful-react-hooks";

const rootDataViewLazy = props => {

    const [first, setFirst] = useState(0);
    const [layout, setLayout] = useState('list');
    const [loading, setLoading] = useState(true);
    const [records, setRecords] = useState([]);
    const [totalRecords, setTotalRecords] = useState(NUMBER_OF_ELEMENTS);
    const [windowWidth, setWindowWidth] = useState(window.innerWidth);

    const isMounted = useRef(false);
    const cancelTokenSource = axios.CancelToken.source();
    const allRecordService = new AllRecordService(cancelTokenSource);
    const cm = useRef(null);
    const history = useHistory();

    const items = [
        {
            label: 'New',
            icon: 'pi pi-fw pi-file',
            command: () => history.push("/create")
        },
        {
            label: 'Edit',
            icon: 'pi pi-fw pi-pencil',
            data: "/calendar",
            command: () => history.push("/edit")
        },
        {
            label: 'Delete',
            icon: 'pi pi-fw pi-trash',
            command: () => history.push("/delete")
        },
        {
            separator: true
        },
        {
            label: 'Quit',
            icon: 'pi pi-fw pi-power-off'
        }
    ];

    useEffect(() => {
        if (isMounted.current) {
            setTimeout(() => {
                setLoading(false);
                setLayout(e.value);
            }, TIMEOUT);
        }
    }, [loading]); // eslint-disable-line react-hooks/exhaustive-deps

    useEffect(() => {
        setTimeout(() => {
            const event = props.page !== null
                ? {
                    first: props.page.currentPage.first,
                    page: props.page.currentPage.page
                }
                : {first: 0, page: 0};
            allRecordService.getRecordsLazy(event, NUMBER_OF_ELEMENTS)
                .then(function (resItems) {
                    setFirst(0);
                    setRecords(resItems['data'].content);
                    setTotalRecords(resItems['data'].totalElements);
                    setLoading(false);
                }).catch(function (error) {
                console.log(error);
            });
        }, TIMEOUT);
    }, []); // eslint-disable-line react-hooks/exhaustive-deps

    // noinspection JSAnnotator
    useWindowResize((event: React.SyntheticEvent) => {
        if (Math.abs(windowWidth - window.innerWidth) > 10)
            setWindowWidth(window.innerWidth);
    });

    const onPage = event => {
        setLoading(true);
        //imitate delay of a backend call
        setTimeout(() => {
            allRecordService.getRecordsLazy(event.originalEvent, NUMBER_OF_ELEMENTS)
                .then(function (resItems) {
                    setFirst(event.originalEvent.first);
                    setRecords(resItems['data'].content);
                    setTotalRecords(resItems['data'].totalElements);
                    setLoading(false);
                }).catch(function (error) {
                console.log(error);
            });
        }, TIMEOUT);
        const page = event !== null
            ? {
                first: event.originalEvent.first,
                page: event.originalEvent.page
            }
            : {first: 0, page: 0};
        props.handlePage(page);
    }

    // TODO
    const renderGridItem = record => {
        return (
            <div/>
        );
    }

    const renderContextMenu = (e, type, id) => {
        if (isAdmin(props)) {
            items[1].command = () => history.push("/edit/" + kebabize(type) + '/' + id);
            items[2].command = () => history.push("/delete?id=" + id);
            cm.current.show(e);
        }
    }

    const renderListItemEntity = value => {

        if (isEmpty(value)) return (
            <div style={{border: 0}} />
        );
        const {id, tags, publicTime, ...record} = value;

        return (
            <div style={{padding: '.5em', border: 0}} className="p-col-12 p-md-4">
                <div id={id}
                     className="p-panel p-component"
                     style={{textAlign: 'left'}}>
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
                            <table aria-haspopup
                                   className="news-entry"
                                   onContextMenu={(e) => renderContextMenu(e, record.type, id)}>
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

    const renderListItem = record => {
        if (isArticle(record.type) || isNewsEntry(record.type) || isNewsLinks(record.type))
            return renderListItemEntity(record);
        return (
            <div style={{border: 0}} aria-labelledby={"empty_record_" + record.id}/>
        );
    }

    const itemTemplate = (record, layout) => {
        if (!record) return;

        if (layout === 'grid')
            return renderGridItem(record);
        else if (layout === 'list')
            return renderListItem(record);
    }

    return (
        <div className="dataview-demo">
            <ContextMenu model={items} ref={cm}/>
            <DataView first={first}
                      itemTemplate={itemTemplate}
                      layout={layout}
                      lazy
                      loading={loading}
                      onPage={onPage}
                      paginator paginatorPosition={'both'}
                      rows={NUMBER_OF_ELEMENTS}
                      totalRecords={totalRecords}
                      value={records}
            />
        </div>
    );
}

const mapStateToProps = state => ({
    date: state.currentDate,
    locale: state.language,
    page: state.currentPage,
    user: state.currentUser,
})

const mapDispatchToProps = dispatch => ({
    handleCalendarDate: value => dispatch(setCalendarDate(value)),
    handlePage: value => dispatch(setPage(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(rootDataViewLazy);
