/*
 * This file was last modified at 2021.03.06 16:57 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * PageAllRecordViewImpl.java
 * $Id$
 */

package su.svn.daybook.domain.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import su.svn.daybook.domain.model.db.db.AllRecordView;

import java.io.Serializable;
import java.util.List;

public class PageAllRecordViewImpl extends PageImpl<AllRecordView> implements Serializable {

    private static final long serialVersionUID = 5902344081066588252L;

    public PageAllRecordViewImpl(List<AllRecordView> content, Pageable pageable) {
        super(content, pageable, content.size());
    }
}
