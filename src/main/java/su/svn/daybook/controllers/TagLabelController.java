/*
 * This file was last modified at 2021.02.27 15:53 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagLabelController.java
 * $Id$
 */

package su.svn.daybook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.DBStringEntry;
import su.svn.daybook.domain.model.ResponseDto;
import su.svn.daybook.domain.model.TagLabelDto;
import su.svn.daybook.services.TagLabelService;

import java.sql.Date;

@Slf4j
@RestController
@RequestMapping("/api/v1/resource")
@Tag(name = "Tag Label Controller", description = "Tag label APIs for the DayBook project.")
public class TagLabelController {

    private final TagLabelService tagLabelService;

    public TagLabelController(TagLabelService tagLabelService) {
        this.tagLabelService = tagLabelService;
    }

    @Operation(
            operationId = "createTagLabel", summary = "Create tag label", tags = {"Resource Controller"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)},
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/tag-label")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> createTagLabel(@RequestBody TagLabelDto dto) {
        log.debug("createArticle({}): authentication={}", dto, SecurityContextHolder.getContext().getAuthentication());
        return tagLabelService.create(dto)
                .map(a -> getBody(a, HttpStatus.CREATED, "Created"));
    }

    @Operation(summary = "Get all of news groups")
    @GetMapping(value = "/tag-label/all")
    @PreAuthorize("permitAll() or hasPermission()")
    public Flux<TagLabelDto> readAll() {
        log.debug("readAll()");
        return tagLabelService.readAll();
    }

    private ResponseEntity<?> getBody(DBStringEntry a, HttpStatus ok, String message) {
        ResponseDto response = ResponseDto.builder()
                .message(message + ": " + a.getId())
                .timestamp(new Date(new java.util.Date().getTime()))
                .status("success")
                .build();
        return ResponseEntity.status(ok).body(response);
    }
}
