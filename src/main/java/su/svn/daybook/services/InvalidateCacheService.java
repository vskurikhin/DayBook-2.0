/*
 * This file was last modified at 2021.03.07 12:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * InvalidateCacheService.java
 * $Id$
 */

package su.svn.daybook.services;

public interface InvalidateCacheService {
    void invalidate(String name);
}
