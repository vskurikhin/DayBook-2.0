/*
 * This file was last modified at 2021.02.23 10:30 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RootDataViewLazy.jsx
 * $Id$
 */

import 'primeicons/primeicons.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';

import {AllRecordService} from '../../service/AllRecordService';
import {isAdmin} from '../../lib/userTool'
import {setCalendarDate} from "../../redux/actions";

import React, {useState, useEffect, useRef} from 'react';
import moment from 'moment';
import {ContextMenu} from 'primereact/contextmenu';
import {DataView} from 'primereact/dataview';
import {Panel} from 'primereact/panel';
import {compose} from "redux";
import {connect} from "react-redux";
import {useHistory, withRouter} from 'react-router-dom';

const NUMBER_OF_ELEMENTS = 3;
const TIMEOUT = 33;

const RootDataViewLazy = (props) => {
    const [records, setRecords] = useState([]);
    const [layout, setLayout] = useState('list');
    const [loading, setLoading] = useState(true);
    const [first, setFirst] = useState(0);
    const [totalRecords, setTotalRecords] = useState(NUMBER_OF_ELEMENTS);
    const [numberOfElements, setNumberOfElements] = useState(NUMBER_OF_ELEMENTS);
    const isMounted = useRef(false);
    const allRecordService = new AllRecordService();
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
            allRecordService.getCarsLazy(null, numberOfElements, totalRecords).then(function (resItems) {
                setTotalRecords(resItems.data.totalElements);
                setNumberOfElements(resItems.data.numberOfElements);
                setRecords(resItems.data.content);
                setFirst(0);
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
            let min = totalRecords - event.originalEvent.page*NUMBER_OF_ELEMENTS;
            min = min < NUMBER_OF_ELEMENTS ? min : NUMBER_OF_ELEMENTS;
            allRecordService.getCarsLazy(event.originalEvent, numberOfElements, min).then(function (resItems) {
                setFirst(event.originalEvent.first);
                setRecords(resItems.data.content);
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

    const renderContextMenu = (e, id) => {
        if (isAdmin(props)) {
            items[1].command = () => history.push("/edit?id=" + id);
            items[2].command = () => history.push("/delete?id=" + id);
            cm.current.show(e);
        }
    }

    const isArticle = (record) => record.type === "Article";
    const isNewsEntry = (record) => record.type === "NewsEntry";
    const isNewsLinks = (record) => record.type === "NewsLinks";

    const renderListItem = (record) => {
        if (isArticle(record) || isNewsEntry(record) || isNewsLinks(record))
            return renderListItemEntity(record);
        return (
            <div style={{padding: '.5em', border: 0}}  key={record.id}/>
        );
    }

    const renderListItemEntity = (record) => {
        return (
            <div style={{padding: '.5em', border: 0}} className="p-col-12 p-md-4" key={record.id}>
                <Panel header={renderTitle(record)} style={{textAlign: 'left'}}>
                    <table aria-haspopup
                           className="news-entry"
                           id={record.id}
                           onContextMenu={(e) => renderContextMenu(e, record.id)}>
                        <tbody>
                        <tr>
                            <td className="my-article-first-th" rowSpan="3">
                                {renderImg(record)}
                            </td>
                            <td className="valueField my-article-second-th" colSpan="2"
                                rowSpan="2">{renderContent(record)}
                                {isArticle(record) ? renderSummaryIncludeAnchor(record) : null}
                                {isNewsLinks(record) ? renderLinkNewsLinks(record) : null}
                            </td>
                            <td/>
                        </tr>
                        <tr>
                            <td/>
                        </tr>
                        <tr>
                            <td>{renderUserName(record)}</td>
                            <td className="my-article-date-th">{moment(record.updateTime).format("dddd, MMM DD at HH:mm a")}</td>
                            <td/>
                        </tr>
                        <tr>
                            <td className="my-article-user-th">{record.tags}</td>
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

    const renderTitle = (record) => {
        if (isArticle(record))
            return record['articleTitle'];
        if (isNewsEntry(record))
            return record['newsEntryTitle'];
        if (isNewsLinks(record))
            return record['newsLinksTitle'];
        return (
            <div style={{padding: '.5em', border: 0}} key={record.id}/>
        );
    }

    const renderImg = (record) => {
        if (isArticle(record))
            return renderImgArticle(record);
        if (isNewsEntry(record))
            return renderImgNewsEntry(record);
        if (isNewsLinks(record))
            return renderImgNewsLinks(record);
        return (
            <div style={{padding: '.5em', border: 0}}  key={record.id}/>
        );
    }

    const renderUserName = (record) => {
        if (isArticle(record))
            return record['articleUserName'];
        if (isNewsEntry(record))
            return record['newsEntryUserName'];
        if (isNewsLinks(record))
            return record['newsLinksUserName'];
        return (
            <div style={{padding: '.5em', border: 0}}  key={record.id}/>
        );
    }

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

    const renderContent = (record) => {
        if (isArticle(record))
            return record['articleSummary'];
        if (isNewsEntry(record))
            return record['newsEntryContent'];
        if (isNewsLinks(record))
            return null;
        return (
            <div style={{padding: '.5em', border: 0}} key={record.id}/>
        );
    }

    const renderSummaryIncludeAnchor = (record) => (
        <ul>
            <li><a href={record['articleInclude']}>{record['articleAnchor']}</a></li>
        </ul>
    )

    const renderLinkNewsLinks = (record) => (
        <ul>
            <li><a href={record.links}>{record.links}</a></li>
        </ul>
    )

    const itemTemplate = (record, layout) => {
        if ( ! record) return;

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
    currentUser: state.currentUser,
    currentDate: state.currentDate
})

const mapDispatchToProps = dispatch => ({
    handleCalendarDate: value => dispatch(setCalendarDate(value))
})

export default compose(
    withRouter,
    connect(mapStateToProps, mapDispatchToProps)
)(RootDataViewLazy);
