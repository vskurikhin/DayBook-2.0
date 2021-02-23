/*
 * This file was last modified at 2021.02.23 11:02 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * BookForm.jsx
 * $Id$
 */

import React from 'react';

export default class BookForm extends React.Component {
    constructor(props) {
        super(props);
        this.onTitleChange = this.onTitleChange.bind(this);
        this.onAuthorChange = this.onAuthorChange.bind(this);
        this.onDescriptionChange = this.onDescriptionChange.bind(this);
        this.onPublishedChange = this.onPublishedChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);

        this.state = {
            title: props.book ? props.book.title : '',
            author: props.book ? props.book.author : '',
            description: props.book ? props.book.description : '',
            published: props.book ? props.book.published : 0,

            error: ''
        };
    }

    onTitleChange(e) {
        const title = e.target.value;
        this.setState(() => ({ title: title }));
    }

    onAuthorChange(e) {
        const author = e.target.value;
        this.setState(() => ({ author: author }));
    }

    onDescriptionChange(e) {
        const description = e.target.value;
        this.setState(() => ({ description: description }));
    }

    onPublishedChange(e) {
        const published = parseInt(e.target.value);
        this.setState(() => ({ published: published }));
    }

    onSubmit(e) {
        e.preventDefault();

        if (!this.state.title || !this.state.author || !this.state.published) {
            this.setState(() => ({ error: 'Please set title & author & published!' }));
        } else {
            this.setState(() => ({ error: '' }));
            this.props.onSubmitBook(
                {
                    title: this.state.title,
                    author: this.state.author,
                    description: this.state.description,
                    published: this.state.published
                }
            );
        }
    }

    render() {
        return (
            <div>
                {this.state.error && <p className='error'>{this.state.error}</p>}
                <form onSubmit={this.onSubmit} className='add-book-form'>

                    <input type="text" placeholder="title" autoFocus
                           value={this.state.title}
                           onChange={this.onTitleChange} />
                    <br />

                    <input type="text" placeholder="author"
                           value={this.state.author}
                           onChange={this.onAuthorChange} />
                    <br />

                    <textarea placeholder="description"
                              value={this.state.description}
                              onChange={this.onDescriptionChange} />
                    <br />

                    <input type="number" placeholder="published"
                           value={this.state.published}
                           onChange={this.onPublishedChange} />
                    <br />
                    <button>Add Book</button>
                </form>
            </div>
        );
    }
}
