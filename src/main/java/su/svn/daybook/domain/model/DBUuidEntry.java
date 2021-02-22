/*
 * This file was last modified at 2021.02.22 14:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DBUuidEntry.java
 * $Id$
 */

package su.svn.daybook.domain.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Base interface for entities
 *
 * @author Victor N. Skurikhin
 */
public interface DBUuidEntry extends DBEntry<UUID>, Serializable {

    /**
     * Returns the key as identifier of the entity.
     *
     * @return - the key.
     */
    UUID getId();

    void setId(UUID id);

    Class<? extends DBUuidEntry> getEClass();
}
