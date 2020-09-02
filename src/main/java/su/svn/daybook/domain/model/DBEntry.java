/*
 * This file was last modified at 2020.09.01 19:25 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DBEntry.java
 * $Id$
 */

package su.svn.daybook.domain.model;

/**
 * Base interface for entities
 *
 * @author Victor N. Skurikhin
 */
public interface DBEntry<T> {

    /**
     * Returns the key as identifier of the entity.
     *
     * @return - the key.
     */
    T getId();
}
