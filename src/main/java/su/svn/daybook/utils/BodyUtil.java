/*
 * This file was last modified at 2021.03.02 17:18 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * BodyUtil.java
 * $Id$
 */

package su.svn.daybook.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import su.svn.daybook.domain.model.DBEntry;
import su.svn.daybook.domain.model.ResponseDto;

import java.sql.Date;

public final class BodyUtil {
    private BodyUtil() {}

    public static ResponseEntity<?> getBody(DBEntry<?> a, HttpStatus ok, String message) {
        ResponseDto response = ResponseDto.builder()
                .message(message + ": " + a.getId())
                .status("success")
                .object(a)
                .timestamp(new Date(new java.util.Date().getTime()))
                .build();
        return ResponseEntity.status(ok).body(response);
    }
}
