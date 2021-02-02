/*
 * This file was last modified at 2021.02.02 21:52 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RootDataViewLazy.jsx
 * $Id$
 */

import 'primeicons/primeicons.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';

import moment from 'moment';
import React, {useState, useEffect} from 'react';
import {DataView, DataViewLayoutOptions} from 'primereact/dataview';
import {Panel} from 'primereact/panel';
import {AllRecordService} from '../../service/AllRecordService';

const RootDataViewLazy = () => {
    const [records, setRecords] = useState([]);
    const [layout, setLayout] = useState('grid');
    const [loading, setLoading] = useState(true);
    const [first, setFirst] = useState(0);
    const [totalRecords, setTotalRecords] = useState(0);
    const [numberOfElements, setNumberOfElements] = useState(3);
    const allRecordService = new AllRecordService();

    useEffect(() => {
        setTimeout(() => {
            allRecordService.getCarsFirstPage(0, 0, numberOfElements).then(function (resItems) {
                setTotalRecords(resItems.data.totalElements);
                setNumberOfElements(resItems.data.numberOfElements);
                setRecords(resItems.data.content);
            }).catch(function (error) {
                console.log(error);
            });
            setFirst(0);
            setLoading(false);
        }, 50);
    }, []); // eslint-disable-line react-hooks/exhaustive-deps

    const onPage = (event) => {
        setLoading(true);
        console.log("event", JSON.stringify(event));

        //imitate delay of a backend call
        setTimeout(() => {
            const startIndex = event.first;
            allRecordService.getCarsLazy(event.originalEvent).then(function (resItems) {
                setTotalRecords(resItems.data.totalElements);
                setNumberOfElements(resItems.data.numberOfElements);
                setRecords(resItems.data.content);
            }).catch(function (error) {
                console.log(error);
            });
            setFirst(startIndex);
            setLoading(false);
        }, 50);
    }

    const renderListItem = (record) => {
        return (
            <div className="p-col-12" key={record.id}>
                <div className="car-details p-paginator-page p-paginator-element p-link p-highlight">
                    <div>
                        <div className="p-grid">
                            <div className="p-col-12">type: <b>{record.articleTitle}</b></div>
                            <div className="p-col-12">
                                <img className="leftimg"
                                     src="/raw-svg/arrow-right.svg"
                                     srcSet="/raw-svg/arrow-right.svg"
                                     alt={record.visible}
                                     width="64"
                                     height="64"
                                />
                                <ul>
                                    <li>Anchor: <b>{record.articleAnchor}</b></li>
                                    <li>Include: <b>{record.articleInclude}</b></li>
                                    <li>Summary: <b>{record.articleSummary}</b></li>
                                    <li>Title: <b>{record.articleTitle}</b></li>
                                    <li>UserName: <b>{record.articleUserName}</b></li>
                                </ul>
                            </div>
                            <div className="p-col-12">updateTime: <b>{record.updateTime}</b></div>
                            <div className="p-col-12">tags: <b>{record.tags}</b></div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    const renderGridItem = (record) => {
        if (record.type === "Article")
            return renderGridItemArticle(record);
        if (record.type === "NewsEntry")
            return renderGridItemNewsEntry(record);
        if (record.type === "NewsLinks")
            return renderGridItemNewsLinks(record);
        return (
            <div style={{padding: '.5em'}} className="p-col-12 p-md-4" key={record.id}>
                <Panel header={record.type} style={{textAlign: 'left'}}>
                    <div className="car-detail">
                        <p align="justify">
                            <img className="my-left-top"
                                 src="/raw-svg/arrow-right.svg"
                                 srcSet="/raw-svg/arrow-right.svg"
                                 alt={record.position}
                                 width="64"
                                 height="64"
                            />{record.tags}
                        </p>
                    </div>
                    <div className="right">{record.tags}</div>
                    <div className="right timestamp-bottom">{moment(record.updateTime).format("dddd, MMM DD at HH:mm a")}</div>
                </Panel>
            </div>
        );
    }

    const renderGridItemArticle = (record) => {
        return (
            <div style={{padding: '.5em'}} className="p-col-12 p-md-4" key={record.id}>
                <Panel header={record.articleTitle} style={{textAlign: 'left'}}>
                    <table className="article">
                        <tbody>
                        <tr>
                            <td className="my-article-first-th" rowSpan="3">
                                <img className="my-left-top"
                                     src="/raw-svg/file.svg"
                                     srcSet="/raw-svg/file.svg"
                                     alt={record.articleTitle}
                                     width="64"
                                     height="64"
                                />
                            </td>
                            <td className="valueField my-article-second-th" colSpan="2" rowSpan="2">{record.articleSummary}
                                Представляю вашему вниманию
                                новый инструмент по созданию HTML таблиц для сайта v3.0 с расширенными
                                возможностями. В данный инструмент я включил самые нужные функции, которые помогут
                                без знаний HTML сгенерировать нужную таблицу. Данная версию была созданная благодаря
                                большой активности пользователей в предыдущих версиях инструмента.
                                <ul>
                                    <li><a href={record.articleInclude}>{record.articleAnchor}</a></li>
                                </ul>
                            </td>
                            <td/>
                        </tr>
                        <tr>
                            <td/>
                        </tr>
                        <tr>
                            <td>{record.tags}</td>
                            <td className="my-article-date-th">{moment(record.updateTime).format("dddd, MMM DD at HH:mm a")}</td>
                            <td/>
                        </tr>
                        <tr>
                            <td className="my-article-user-th">{record.articleUserName}</td>
                            <td/>
                            <td/>
                            <td/>
                        </tr>
                        </tbody>
                    </table>
                </Panel>
            </div>
        );
    }

    const renderGridItemNewsEntry = (record) => {
        return (
            <div style={{padding: '.5em'}} className="p-col-12 p-md-4" key={record.id}>
                <Panel header={record.newsEntryTitle} style={{textAlign: 'left'}}>
                    <table className="news-entry">
                        <tbody>
                        <tr>
                            <td className="my-news-entry-first-th" rowSpan="3">
                                <img className="my-left-top"
                                     src="/raw-svg/pause.svg"
                                     srcSet="/raw-svg/pause.svg"
                                     alt={record.newsEntryTitle}
                                     width="64"
                                     height="96"
                                />
                            </td>
                            <td className="valueField my-news-entry-second-th" colSpan="2" rowSpan="2">{record.newsEntryContent}
                                Представляю вашему вниманию
                                новый инструмент по созданию HTML таблиц для сайта v3.0 с расширенными
                                возможностями. В данный инструмент я включил самые нужные функции, которые помогут
                                без знаний HTML сгенерировать нужную таблицу. Данная версию была созданная благодаря
                                большой активности пользователей в предыдущих версиях инструмента.
                            </td>
                            <td/>
                        </tr>
                        <tr>
                            <td/>
                        </tr>
                        <tr>
                            <td>{record.tags}</td>
                            <td className="my-news-entry-date-th">{moment(record.updateTime).format("dddd, MMM DD at HH:mm a")}</td>
                            <td/>
                        </tr>
                        <tr>
                            <td className="my-news-entry-user-th">{record.newsEntryUserName}</td>
                            <td/>
                            <td/>
                            <td/>
                        </tr>
                        </tbody>
                    </table>
                </Panel>
            </div>
        );
    }

    const renderGridItemNewsLinks = (record) => {
        return (
            <div style={{padding: '.5em'}} className="p-col-12 p-md-4" key={record.id}>
                <Panel header={record.newsLinksTitle} style={{textAlign: 'left'}}>
                    <table className="news-links">
                        <tbody>
                        <tr>
                            <td className="my-news-links-first-th" rowSpan="3">
                                <img className="my-left-top"
                                     src="/raw-svg/link.svg"
                                     srcSet="/raw-svg/link.svg"
                                     alt={record.newsLinksTitle}
                                     width="64"
                                     height="72"
                                />
                            </td>
                            <td className="valueField my-news-links-second-th" colSpan="2" rowSpan="2">
                                <ul>
                                    <li><a href={record.links}>{record.links}</a></li>
                                </ul>
                            </td>
                            <td/>
                        </tr>
                        <tr>
                            <td/>
                        </tr>
                        <tr>
                            <td>{record.tags}</td>
                            <td className="my-news-links-date-th">{moment(record.updateTime).format("dddd, MMM DD at HH:mm a")}</td>
                            <td/>
                        </tr>
                        <tr>
                            <td className="my-news-links-user-th">{record.updateTime}</td>
                            <td/>
                            <td/>
                            <td/>
                        </tr>
                        </tbody>
                    </table>
                </Panel>
            </div>
        );
    }

    const itemTemplate = (record, layout) => {
        if (!record) {
            return;
        }

        if (layout === 'grid')
            return renderGridItem(record);
        else if (layout === 'list')
            return renderListItem(record);
    }

    const renderHeader = () => {
        let onOptionChange = (e) => {
            setLoading(true);

            setTimeout(() => {
                setLoading(false);
                setLayout(e.value);
            }, 50);
        };

        return (
            <div style={{textAlign: 'left'}}>
                <DataViewLayoutOptions layout={layout} onChange={onOptionChange}/>
            </div>
        );
    }

    return (
        <div className="dataview-demo">
            <DataView value={records} layout={layout} itemTemplate={itemTemplate}
                      lazy paginator paginatorPosition={'both'} rows={numberOfElements} totalRecords={totalRecords}
                      first={first} onPage={onPage} loading={loading}/>
        </div>
    );
}

export default RootDataViewLazy;
