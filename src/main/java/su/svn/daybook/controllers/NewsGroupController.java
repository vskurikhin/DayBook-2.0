/*
 * This file was last modified at 2021.02.26 17:46 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupController.java
 * $Id$
 */

package su.svn.daybook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.db.NewsGroup;
import su.svn.daybook.services.NewsGroupService;

@Slf4j
@RestController
@RequestMapping("/api/v1/news-group")
@Tag(name = "News group Controller", description = "News group APIs for the DayBook project.")
public class NewsGroupController {

    private final NewsGroupService newsGroupService;

    public NewsGroupController(NewsGroupService newsGroupServicel) {
        this.newsGroupService = newsGroupServicel;
    }

    @Operation(summary = "Get all of news groups")
    @GetMapping(value = "/all")
    @PreAuthorize("permitAll() or hasPermission()")
    public Flux<NewsGroup> readAll() {
        log.debug("readRecords()");
        return newsGroupService.getAll();
    }

    @Operation(summary = "Get all of news groups like by prefix")
    @GetMapping(value = "/like")
    @PreAuthorize("permitAll() or hasPermission()")
    public Flux<NewsGroup> readAllLike(String prefix) {
        log.debug("readRecords()");
        return newsGroupService.getAllLike(prefix);
    }

    @Operation(summary = "Get page of news group")
    @GetMapping(value = "/page")
    @PreAuthorize("permitAll() or hasPermission()")
    public Mono<Page<NewsGroup>> readPage(
            @RequestParam("page") @Parameter(name = "page", required = true, example = "0") int page,
            @RequestParam("size") @Parameter(name = "size", required = true, example = "999") int size) {
        log.debug("readRecords()");
        return newsGroupService.getPage(page, size);
    }
}