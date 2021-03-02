/*
 * This file was last modified at 2021.03.02 17:18 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupController.java
 * $Id$
 */

package su.svn.daybook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.ArticleDto;
import su.svn.daybook.domain.model.NewsGroupDto;
import su.svn.daybook.domain.model.ResponseDto;
import su.svn.daybook.domain.model.db.db.NewsGroup;
import su.svn.daybook.services.NewsGroupService;
import su.svn.daybook.utils.BodyUtil;

@Slf4j
@RestController
@RequestMapping("/api/v1/resource")
@Tag(name = "News Group Controller", description = "News group APIs for the DayBook project.")
public class NewsGroupController {

    private final NewsGroupService newsGroupService;

    public NewsGroupController(NewsGroupService newsGroupServicel) {
        this.newsGroupService = newsGroupServicel;
    }

    @Operation(
            operationId = "createNewsGroup", summary = "Create News Group", tags = {"News Group Controller"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)},
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/news-group")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> createNewsGroup(@RequestBody NewsGroupDto dto) {
        log.debug("readAll()");
        return newsGroupService.create(dto)
                .map(a -> BodyUtil.getBody(a, HttpStatus.CREATED, "Created"));
    }

    @Operation(summary = "Get all of news groups")
    @GetMapping(value = "/news-group/all")
    @PreAuthorize("permitAll() or hasPermission()")
    public Flux<NewsGroup> readAll() {
        log.debug("readAll()");
        return newsGroupService.readAll();
    }

    @Operation(summary = "Get all of news groups like by prefix")
    @GetMapping(value = "/news-group/like")
    @PreAuthorize("permitAll() or hasPermission()")
    public Flux<NewsGroup> readAllLike(String prefix) {
        log.debug("readAllLike()");
        return newsGroupService.readAllLike(prefix);
    }

    @Operation(summary = "Get page of news group")
    @GetMapping(value = "/news-group/page")
    @PreAuthorize("permitAll() or hasPermission()")
    public Mono<Page<NewsGroup>> readPage(
            @RequestParam("page") @Parameter(name = "page", required = true, example = "0") int page,
            @RequestParam("size") @Parameter(name = "size", required = true, example = "999") int size) {
        log.debug("readPage()");
        return newsGroupService.readPage(page, size);
    }

    @Operation(
            operationId = "updateNewsGroup", summary = "Update News Group", tags = {"News Group Controller"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)},
            security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "/news-group")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> updateNewsGroup(@RequestBody NewsGroupDto dto) {
        log.debug("readAll()");
        return newsGroupService.update(dto)
                .map(a -> BodyUtil.getBody(a, HttpStatus.OK, "Updated"));
    }
}
