/*
 * This file was last modified at 2020.11.15 21:41 by Victor N. Skurikhin.
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

    Class<? extends DBStringEntry> getEClass();
}
