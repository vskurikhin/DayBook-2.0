/*
 * This file was last modified at 2020.12.23 09:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DBStringEntry.java
 * $Id$
 */

package su.svn.daybook.domain.model;

/**
 * Base interface for entities
 *
 * @author Victor N. Skurikhin
 */
public interface DBStringEntry extends DBEntry<String> {

    /**
     * Returns the key as identifier of the entity.
     *
     * @return - the key.
     */
    String getId();

    void setId(String id);

    Class<? extends DBStringEntry> getEClass();
}
