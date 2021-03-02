/*
 * This file was last modified at 2021.03.02 23:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CreateTabView.jsx
 * $Id$
 */

import CreateArticleView from "./Article/CreateArticle";
import CreateNewsEntryView from "./NewsEntry/CreateNewsEntry";
import CreateNewsGroup from "./NewsGroup/CreateNewsGroup";
import CreateTagLabel from "./TagLabel/CreateTagLabel";

import React, {Component} from 'react';
import {TabView, TabPanel} from 'primereact/tabview';
import {Translator} from "@eo-locale/core";
import {locales} from "../../config/locales";
import {connect} from "react-redux";
import {Text, TranslationsProvider} from "@eo-locale/react";

const DEFAULT_ACTIVE_INDEX = 2;

class CreateTabView extends Component {

    state = {
        activeIndex: DEFAULT_ACTIVE_INDEX
    }

    constructor(props) {
        super(props);
        this.defaultActiveIndex = this.defaultActiveIndex.bind(this);
    }

    defaultActiveIndex() {
        this.setActiveIndex(DEFAULT_ACTIVE_INDEX);
    }

    setActiveIndex(index) {
        this.setState({activeIndex: index});
    }

    render() {
        const translator = new Translator(this.props.locale.language, locales);
        return (
            <div className="tab-view">
                <TranslationsProvider language={this.props.locale.language} locales={locales}>
                    <h1><Text id='Create'/></h1>
                    <TabView activeIndex={this.state.activeIndex} onTabChange={(e) => this.setActiveIndex(e.index)}>
                        <TabPanel header={translator.getMessageById('News_Group')}>
                            <CreateNewsGroup defaultActiveIndex={this.defaultActiveIndex}/>
                        </TabPanel>
                        <TabPanel header={translator.getMessageById('Tag_Label')}>
                            <CreateTagLabel defaultActiveIndex={this.defaultActiveIndex}/>
                        </TabPanel>
                        <TabPanel header={translator.getMessageById('Article')}>
                            <CreateArticleView/>
                        </TabPanel>
                        <TabPanel header={translator.getMessageById('News_Entry')}>
                            <CreateNewsEntryView/>
                        </TabPanel>
                        <TabPanel header={translator.getMessageById('News_Links')}>
                            <p>At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium
                                voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint
                                occaecati
                                cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi,
                                id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita
                                distinctio.
                                Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo
                                minus.</p>
                        </TabPanel>
                    </TabView>
                </TranslationsProvider>
            </div>
        );
    }
}

const mapStateToProps = state => ({
    locale: state.language
})

export default connect(mapStateToProps)(CreateTabView);
