/*
 * This file was last modified at 2021.02.25 16:07 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DBUserOwnedEntry.java
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
public interface DBUserOwnedEntry extends DBEntry<UUID>, Serializable {

    String getUserName();

    void setUserName(String userName);
}
