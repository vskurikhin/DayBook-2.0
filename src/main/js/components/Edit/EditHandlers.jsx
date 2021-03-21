/*
 * This file was last modified at 2021.03.21 17:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * EditHandlers.jsx
 * $Id$
 */

import React, {Component} from 'react';
import moment from "moment";
import {DEFAULT_NEWS_GROUP_ID} from "../../config/consts";
import {addLocale} from 'primereact/api';
import {RESOURCE_RECORD_SUCCESS} from "../../redux/resourceRecord";
import {recordService} from "../../service/RecordService";

export default class EditHandlers extends Component {

    constructor(props) {
        super(props);
        addLocale('ru', {
            firstDayOfWeek: 1,
            dayNames: ['воскресенье', 'понедельник', 'вторник', 'среда', 'четверг', 'пятница', 'суббота'],
            dayNamesShort: ['вс.', 'пн.', 'вт.', 'ср.', 'чт.', 'пт.', 'сб.'],
            dayNamesMin: ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'],
            monthNames: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь', 'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декадрь'],
            monthNamesShort: ['янв.', 'фев.', 'март', 'апр.', 'май', 'июнь', 'июль', 'авг.', 'сен.', 'окт.', 'ноя.', 'дек.'],
            today: 'Сегодня',
            clear: 'Очистить'
        });
    }

    handleCreateNewsGroupChange = value => {
        this.setState({newsGroupNames: value.data});
        const mayBeFirst = value.data.filter(x => x.id === DEFAULT_NEWS_GROUP_ID);
        this.setState({selectedNewsGroup: mayBeFirst.length > 0 ? mayBeFirst[0] : null});
    }

    handleEditNewsGroupChange = value => {
        this.setState({newsGroupNames: value.data});
    }


    handleRecordChange = value => {
        this.setState({
            data: {
                ...value.data,
                publicTime: moment(value.data.publicTime).toDate()
            }
        });
        this.mayBeSetSelectedNewsGroup();
        const selectedTags = value.data.tags.map(label => {return {label: label}});
        this.setState({selectedTags: selectedTags});
    }

    mayBeSetSelectedNewsGroup = () => {
        const mayBeItem = this.state.newsGroupNames.filter(x => x.id === this.state.data.newsGroupId);
        this.setState({selectedNewsGroup: mayBeItem.length > 0 ? mayBeItem[0] : null})
    }


    handleTagLabelChange = value => {
        this.setState({tagLabels: value.data});
    }


    onChangeDefault = event => {
        this.setState({
            data: {
                ...this.state.data,
                [event.target.name]: event.target.value,
            }
        });
    }

    onNewsGroupChange = event => {
        this.setState({selectedNewsGroup: event.value});
        this.setState({
            data: {
                ...this.state.data,
                newsGroupId: event.value.id
            }
        });
    }

    onPublicTimeChange = event => {
        this.setState({
            data: {
                ...this.state.data,
                publicTime: event.value
            }
        });
    }

    onTagLabelsChange = event => {
        this.setState({selectedTags: event.value});
        const tags = event.value.map(t => t.label);
        this.setState({
            data: {
                ...this.state.data,
                tags: tags
            }
        });
    }

    searchTagLabels = event => {
        setTimeout(() => {
            let filteredTagLabels;
            if ( ! event.query.trim().length) {
                filteredTagLabels = [...this.state.tagLabels];
            }
            else {
                filteredTagLabels = this.state.tagLabels.filter((tagLabel) => {
                    return tagLabel.label.toLowerCase().startsWith(event.query.toLowerCase());
                });
            }

            this.setState({filteredTagLabels: filteredTagLabels});
        }, 250);
    }

    setStateRedirectToReferrer = value => {
        this.setState({redirectToReferrer: true});
    }

    setStateRedirectToReferrerRecord = value => {
        this.setState({redirectToReferrer: true});
        return {
            type: RESOURCE_RECORD_SUCCESS,
            resourceRecord: value
        }
    }
}