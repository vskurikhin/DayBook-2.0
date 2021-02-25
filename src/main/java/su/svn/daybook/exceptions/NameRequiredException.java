/*
 * This file was last modified at 2021.02.25 19:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NameRequiredException.java
 * $Id$
 */

package su.svn.daybook.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;

import javax.annotation.Nonnull;
import java.util.UUID;

public class NameRequiredException extends ResponseStatusException {

    private static final long serialVersionUID = 6300494403686560699L;

    public NameRequiredException(HttpStatus status, String message, Throwable e) {
        super(status, message, e);
    }

    @Nonnull
    public static Exception notFoundException(UUID id) {
        String message = "Not found for id: " + id;
        Exception notFoundException = new NotFoundException(message);

        return new NameRequiredException(HttpStatus.NOT_FOUND, message, notFoundException);
    }
}
