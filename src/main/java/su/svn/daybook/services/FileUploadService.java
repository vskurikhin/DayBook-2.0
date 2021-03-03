/*
 * This file was last modified at 2021.03.03 19:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * FileUploadService.java
 * $Id$
 */

package su.svn.daybook.services;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FileUploadService {
    // this is for single file upload.
    Flux<String> getLines(Mono<FilePart> filePartMono);
}
