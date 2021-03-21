/*
 * This file was last modified at 2021.03.21 17:13 by Victor N. Skurikhin.
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.ResponseDto;
import su.svn.daybook.domain.model.TagLabelDto;
import su.svn.daybook.domain.model.TagsForRecordDto;
import su.svn.daybook.services.TagLabelService;
import su.svn.daybook.services.TaggetService;
import su.svn.daybook.utils.BodyUtil;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/v1/resource")
@Tag(name = "Tag Label Controller", description = "Tag label APIs for the DayBook project.")
public class TagLabelController {

    private final TagLabelService tagLabelService;

    private final TaggetService taggetService;

    public TagLabelController(TagLabelService tagLabelService, TaggetService taggetService) {
        this.tagLabelService = tagLabelService;
        this.taggetService = taggetService;
    }

    @Operation(
            operationId = "addTags", summary = "Add tags to record", tags = {"Resource Controller"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)},
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/add-tags")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> addTags(@RequestBody TagsForRecordDto dto) {
        log.debug("addTags({}): authentication={}", dto, SecurityContextHolder.getContext().getAuthentication());
        return taggetService.addTags(dto)
                .map(a -> BodyUtil.getBody(a, HttpStatus.CREATED, "Added for"));
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
                .map(a -> BodyUtil.getBody(a, HttpStatus.CREATED, "Created"));
    }

    @Operation(summary = "Get all of tag label")
    @GetMapping(value = "/tag-label/all")
    @PreAuthorize("permitAll() or hasPermission()")
    public Flux<TagLabelDto> readAll() {
        log.debug("readAll()");
        return tagLabelService.readAll();
    }

    @Operation(summary = "Find tag by labels")
    @GetMapping(value = "/tag-label/in")
    @PreAuthorize("permitAll() or hasPermission()")
    public Flux<TagLabelDto> readLabelIn(String[] labels) {
        log.debug("readLabelIn()");
        return tagLabelService.readLabelIn(Set.of(labels));
    }
}
