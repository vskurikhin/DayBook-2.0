/*
 * This file was last modified at 2021.03.01 23:42 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * EditHandlers.jsx
 * $Id$
 */

import React, {Component} from 'react';
import {DEFAULT_NEWS_GROUP_ID} from "../../config/consts";

export default class EditHandlers extends Component {

    constructor(props) {
        super(props);
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
        this.setState({data: value.data});
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
}