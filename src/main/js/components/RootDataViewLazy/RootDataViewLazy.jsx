/*
 * This file was last modified at 2021.03.07 12:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RootDataViewLazy.jsx
 * $Id$
 */

import 'primeicons/primeicons.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';
import './Button.css';

import {API_V1_RESOURCE_RECORDS} from '../../config/api';
import {AllRecordService} from '../../service/AllRecordService';
import {isAdmin} from '../../lib/userTool'
import {kebabize} from "../../lib/kebabize";
import {setCalendarDate} from "../../redux/actions";

import React, {useState, useEffect, useRef} from 'react';
import axios from "axios";
import moment from 'moment';
import {Button} from 'primereact/button';
import {ContextMenu} from 'primereact/contextmenu';
import {DataView} from 'primereact/dataview';
import {DateTime} from '@eo-locale/react';
import {compose} from "redux";
import {connect} from "react-redux";
import {map} from 'underscore'
import {useHistory, withRouter} from 'react-router-dom';

const NUMBER_OF_ELEMENTS = 10;
const TIMEOUT = 33;

const RootDataViewLazy = props => {
    const [records, setRecords] = useState([]);
    const [layout, setLayout] = useState('list');
    const [loading, setLoading] = useState(true);
    const [first, setFirst] = useState(0);
    const [totalRecords, setTotalRecords] = useState(NUMBER_OF_ELEMENTS);
    const [numberOfElements, setNumberOfElements] = useState(NUMBER_OF_ELEMENTS);
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
            allRecordService.getRecordsLazy(null, numberOfElements).then(function (resItems) {
                setFirst(0);
                setRecords(resItems['data'].content);
                setTotalRecords(resItems['data'].totalElements);
                setLoading(false);
            }).catch(function (error) {
                console.log(error);
            });
        }, TIMEOUT);
    }, []); // eslint-disable-line react-hooks/exhaustive-deps

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

    const isArticle = (type) => type === "Article";
    const isNewsEntry = (type) => type === "NewsEntry";
    const isNewsLinks = (type) => type === "NewsLinks";

    const renderListItem = (record) => {
        if (isArticle(record.type) || isNewsEntry(record.type) || isNewsLinks(record.type))
            return renderListItemEntity(record);
        return (
            <div aria-labelledby={"empty_record_" + record.id}/>
        );
    }

    const renderListItemEntity = (value) => {

        if (value === {}) return (
            <div/>
        );
        const {id, tags, updateTime, ...record} = value;

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
                                        {renderImg(id, record)}
                                    </td>
                                    <td className="valueField my-news-entry-second-th"
                                        colSpan="2"
                                        rowSpan="2">
                                        <div align="justify" dangerouslySetInnerHTML={{
                                            __html: renderContent(id, record)
                                        }}
                                        />
                                        {renderAfterContent(id, record)}</td>
                                    <td/>
                                </tr>
                                <tr>
                                    <td/>
                                </tr>
                                <tr>
                                    <td>{renderUserName(id, record)}</td>
                                    <td className="my-news-entry-date-th"
                                    ><DateTime
                                        language={props.locale.language}
                                        value={moment(updateTime)}
                                        day="numeric"
                                        month="long"
                                        weekday="long"
                                        year="numeric"
                                    /></td>
                                    <td/>
                                </tr>
                                <tr>
                                    <td/>
                                    <td className="my-news-entry-tags-th" colSpan="2">{renderTags(tags)}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    const renderTitle = (id, value) => {
        const {type, ...record} = value;
        if (isArticle(type))
            return record['articleTitle'];
        if (isNewsEntry(type))
            return record['newsEntryTitle'];
        if (isNewsLinks(type))
            return record['newsLinksTitle'];
        return "title for " + id;
    }

    const renderImg = (id, value) => {
        const {type, ...record} = value;
        if (isArticle(type))
            return renderImgArticle(record);
        if (isNewsEntry(type))
            return renderImgNewsEntry(record);
        if (isNewsLinks(type))
            return renderImgNewsLinks(record);
        return (
            <div style={{padding: '.5em', border: 0}}>image for id</div>
        );
    }

    const renderUserName = (id, value) => {
        const {type, ...record} = value;
        if (isArticle(type))
            return record['articleUserName'];
        if (isNewsEntry(type))
            return record['newsEntryUserName'];
        if (isNewsLinks(type))
            return record['newsLinksUserName'];
        return "username for " + id;
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

    const renderAfterContent = (id, value) => {
        const {type, ...record} = value;
        if (isArticle(type))
            return renderSummaryIncludeAnchor(id, record);
        if (isNewsLinks(type))
            return renderLinkNewsLinks(id, record);
        return (
            <div aria-labelledby={"after_content_for_" + id}/>
        );
    }

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

    const renderImgArticle = (record) => (
        <img alt={record['articleTitle']}
             className="my-left-top"
             src="/raw-svg/file.svg"
             srcSet="/raw-svg/file.svg"
             height="64"
             width="64"
        />
    )

    const renderImgNewsEntry = (record) => (
        <img alt={record['newsEntryTitle']}
             className="my-left-top"
             src="/raw-svg/pause.svg"
             srcSet="/raw-svg/pause.svg"
             height="96"
             width="64"
        />
    )

    const renderImgNewsLinks = (record) => (
        <img alt={record['newsLinksTitle']}
             className="my-left-top"
             src="/raw-svg/link.svg"
             srcSet="/raw-svg/link.svg"
             height="72"
             width="64"
        />
    )

    const renderTags = (tags) => {
        const items = [];
        for (let object in tags) {
            // noinspection JSUnfilteredForInLoop
            let index = Object.keys(tags).indexOf(object);
            items.push({index: index, value: tags[index]});
        }
        return (
            <div>{map(items, ({index, value}) => (
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

    const renderButton = (index, value) => (
        <Button
            key={index}
            label={value}
            className="p-button-sm p-button-rounded p-button-secondary"
            disabled
        />
    )

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
    record: state.updatedRecord,
    user: state.currentUser,
})

const mapDispatchToProps = dispatch => ({
    handleCalendarDate: value => dispatch(setCalendarDate(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(RootDataViewLazy);
