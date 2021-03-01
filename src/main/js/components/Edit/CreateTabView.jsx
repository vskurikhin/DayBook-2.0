/*
 * This file was last modified at 2021.03.01 20:59 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CreateTabView.jsx
 * $Id$
 */

import CreateArticleView from "./ArticleView/CreateArticleView";
import CreateNewsEntryView from "./NewsEntryView/CreateNewsEntryView";

import React, {Component} from 'react';
import {TabView, TabPanel} from 'primereact/tabview';

export default class CreateTabView extends Component {

    constructor(props) {
        super(props);
    }

    render() {

        return (
            <div className="card">
                <h1>Create</h1>
                <TabView>
                    <TabPanel header="News Group">
                        <p>At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati
                            cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio.
                            Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus.</p>
                    </TabPanel>
                    <TabPanel header="Article">
                        <CreateArticleView/>
                    </TabPanel>
                    <TabPanel header="News Entry">
                        <CreateNewsEntryView/>
                    </TabPanel>
                    <TabPanel header="News Links">
                        <p>At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati
                            cupiditate non provident, similique sunt in culpa qui officia deserunt mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et expedita distinctio.
                            Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus.</p>
                    </TabPanel>
                </TabView>
            </div>
        );
    }
}
