/*
 * This file was last modified at 2021.03.09 22:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RootDataViewLazy.jsx
 * $Id$
 */

import 'primeicons/primeicons.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';
import './Button.css';

import RenderAfterContent from "./RenderAfterContent";
import RenderImg from "./RenderImg";
import RenderPublicTime from "./RenderPublicTime";
import RenderTags, {convertToItems} from "./RenderTags";
import {API_V1_RESOURCE_RECORDS} from '../../config/api';
import {AllRecordService} from '../../service/AllRecordService';
import {isAdmin} from '../../lib/userTool'
import {isArticle, isNewsEntry, isNewsLinks} from "../../lib/is";
import {kebabize} from "../../lib/kebabize";
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


const NUMBER_OF_ELEMENTS = 10;
const TIMEOUT = 33;
export const WINDOW_WIDTH_LIMIT = {
    BIG: 990,
    MIDDLE: 660,
    SMALL: 550
};

const rootDataViewLazy = props => {

    const [first, setFirst] = useState(0);
    const [layout, setLayout] = useState('list');
    const [loading, setLoading] = useState(true);
    const [numberOfElements, setNumberOfElements] = useState(NUMBER_OF_ELEMENTS);
    const [records, setRecords] = useState([]);
    const [totalRecords, setTotalRecords] = useState(NUMBER_OF_ELEMENTS);
    const [windowWidth, setWindowWidth] = useState(window.innerWidth);
    const isMounted = useRef(false);
    const cancelTokenSource = axios.CancelToken.source();
    const allRecordService = new AllRecordService(API_V1_RESOURCE_RECORDS, cancelTokenSource);
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
                ? {first: props.page.currentPage.first,
                    page: props.page.currentPage.page}
                : {first: 0, page: 0};
            allRecordService.getRecordsLazy(event, numberOfElements).then(function (resItems) {
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
        setWindowWidth(window.innerWidth);
    });

    const onPage = (event) => {
        setLoading(true);
        //imitate delay of a backend call
        setTimeout(() => {
            allRecordService.getRecordsLazy(event.originalEvent, numberOfElements).then(function (resItems) {
                setFirst(event.originalEvent.first);
                setRecords(resItems['data'].content);
                setTotalRecords(resItems['data'].totalElements);
                setLoading(false);
            }).catch(function (error) {
                console.log(error);
            });
        }, TIMEOUT);
        const page = event !== null
            ? {first: event.originalEvent.first,
                page: event.originalEvent.page}
            : {first: 0, page: 0};
        props.handlePage(page);
    }

    // TODO
    const renderGridItem = (record) => {
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

    const renderTitle = (id, value) => {
        const {type, ...record} = value;
        if (record.title === null)
            return "title for " + id;
        return record.title;
    }

    const renderUserName = (id, value) => {
        const {type, ...record} = value;
        if (record.userName === null)
            return "username for " + id;
        return record.userName;
    }

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

    const renderListItemEntity = value => {

        if (value === {}) return (
            <div/>
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
                                <tbody>
                                <tr>
                                    <td className="my-news-entry-first-th" rowSpan="3">
                                        <RenderImg
                                            id={id}
                                            value={record}
                                            windowWidth={windowWidth}
                                            {...props}
                                        />
                                    </td>
                                    <td className="valueField my-news-entry-second-th"
                                        colSpan="2"
                                        rowSpan="2">
                                        <div align="justify" dangerouslySetInnerHTML={{
                                            __html: renderContent(id, record)
                                        }}
                                        />
                                        <RenderAfterContent id={id} value={record} />
                                    </td>
                                </tr>
                                <tr>
                                    <td/>
                                </tr>
                                <tr>
                                    <td>{renderUserName(id, record)}</td>
                                    <td className="my-news-entry-date-th">
                                    <RenderPublicTime
                                        publicTime={publicTime}
                                        windowWidth={windowWidth}
                                        {...props}
                                    />
                                    </td>
                                </tr>
                                <tr>
                                    <td/>
                                    <td className="my-news-entry-tags-th" colSpan="2">
                                        <RenderTags items={convertToItems(tags)} />
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    const renderListItem = (record) => {
        if (isArticle(record.type) || isNewsEntry(record.type) || isNewsLinks(record.type))
            return renderListItemEntity(record);
        return (
            <div aria-labelledby={"empty_record_" + record.id}/>
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
                      rows={numberOfElements}
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
    record: state.updatedRecord,
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
