/*
 * This file was last modified at 2021.02.27 15:53 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TaggetService.java
 * $Id$
 */

package su.svn.daybook.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import su.svn.daybook.domain.dao.db.db.TaggetDao;
import su.svn.daybook.domain.model.db.db.Tagget;

@Slf4j
@Service
public class TaggetService extends AbstractService<Tagget> {

    private final TaggetDao entryDao;

    public TaggetService(TaggetDao entryDao) {
        this.entryDao = entryDao;
    }
}
