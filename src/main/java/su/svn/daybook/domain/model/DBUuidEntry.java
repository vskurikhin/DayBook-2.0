/*
 * This file was last modified at 2020.12.23 09:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DBUuidEntry.java
 * $Id$
 */

package su.svn.daybook.domain.model;

import java.util.UUID;

/**
 * Base interface for entities
 *
 * @author Victor N. Skurikhin
 */
public interface DBUuidEntry extends DBEntry<UUID> {

    /**
     * Returns the key as identifier of the entity.
     *
     * @return - the key.
     */
    UUID getId();

    void setId(UUID id);

    Class<? extends DBUuidEntry> getEClass();
}
