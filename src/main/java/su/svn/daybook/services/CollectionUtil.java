/*
 * This file was last modified at 2021.02.28 23:25 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CollectionUtil.java
 * $Id$
 */

package su.svn.daybook.services;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashSet;

public class CollectionUtil {
    private CollectionUtil() {}

    @Nonnull
    public static HashSet<String> getTags(String[] tags) {
        return tags != null ? new HashSet<>(Arrays.asList(tags)) : new HashSet<>();
    }
}
