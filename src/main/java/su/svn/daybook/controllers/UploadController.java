/*
 * This file was last modified at 2021.03.03 19:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UploadController.java
 * $Id$
 */

package su.svn.daybook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.ResponseDto;
import su.svn.daybook.services.FileUploadService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/multipart-file")
@RequiredArgsConstructor
public class UploadController {

    private final FileUploadService fileUploadService;

    @Operation(
            operationId = "upload", summary = "Single file upload ", tags = {"Upload Controller"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)},
            security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    // use single Mono<FilePart> for single file upload
    @PostMapping(value = "/upload-mono", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Mono<List<String>> upload(@RequestPart("file") Mono<FilePart> filePartMono) {
        /*
          To see the response beautifully we are returning strings as Mono List
          of String. We could have returned Flux<String> from here.
          If you are curious enough then just return Flux<String> from here and
          see the response on Postman
         */
        return fileUploadService.getLines(filePartMono).collectList();
    }
}
