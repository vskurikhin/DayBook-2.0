/*
 * This file was last modified at 2021.02.25 16:07 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * SecurityContextUtil.java
 * $Id$
 */

package su.svn.daybook.services;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextUtil {

    public static String getName(SecurityContext context) {
        if (isNotValidContextAuthentication(context)) {
            return null;
        }
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static boolean isNotValidContextAuthentication(SecurityContext context) {
        return null == context.getAuthentication();
    }

}
