/*
 * This file was last modified at 2021.02.27 15:53 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ResourceController.java
 * $Id$
 */

package su.svn.daybook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.db.RecordNewsEntryService;
import su.svn.daybook.domain.model.ArticleDto;
import su.svn.daybook.domain.model.DBUuidEntry;
import su.svn.daybook.domain.model.NewsEntryDto;
import su.svn.daybook.domain.model.ResponseDto;
import su.svn.daybook.domain.model.db.db.AllRecordView;
import su.svn.daybook.domain.security.ProfileResponse;
import su.svn.daybook.services.ArticleService;
import su.svn.daybook.services.NewsEntryService;

import java.sql.Date;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/resource")
@Tag(name = "Resource Controller", description = "Resource APIs for the DayBook project.")
public class ResourceController {

    private final RecordNewsEntryService recordNewsEntryService;

    private final ArticleService articleService;

    private final NewsEntryService newsEntryService;

    public ResourceController(
            RecordNewsEntryService recordNewsEntryService,
            ArticleService articleService,
            NewsEntryService newsEntryService) {
        this.recordNewsEntryService = recordNewsEntryService;
        this.articleService = articleService;
        this.newsEntryService = newsEntryService;
    }

    @Operation(summary = "profile", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> profile(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            log.debug("User has authorities: {}", userDetails.getAuthorities());
            return Mono.just(ResponseEntity.ok(new ProfileResponse(userDetails.getUsername())));
        } else if (authentication != null && authentication.getPrincipal() instanceof String) {
            log.debug("User has authorities: {}", authentication.getAuthorities());
            return Mono.just(ResponseEntity.ok(new ProfileResponse(authentication.getPrincipal().toString())));
        }
        return Mono.just(ResponseEntity.ok("{}"));
    }

    @Operation(
            operationId = "createArticle", summary = "Create article record", tags = {"Resource Controller"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)},
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/record/article")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> createArticle(@RequestBody ArticleDto dto) {
        log.debug("createArticle({}): authentication={}", dto, SecurityContextHolder.getContext().getAuthentication());
        return articleService.create(dto)
                .map(a -> getBody(a, HttpStatus.CREATED, "Created"));
    }

    @Operation(
            operationId = "createNewsEntry", summary = "Create news entry record", tags = {"Resource Controller"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)},
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/record/news-entry")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> createNewsEntry(@RequestBody NewsEntryDto dto) {
        log.debug("createNewsEntry({}}): authentication={}", dto, SecurityContextHolder.getContext().getAuthentication());
        return newsEntryService.create(dto)
                .map(a -> getBody(a, HttpStatus.CREATED, "Created"));
    }

    @Operation(
            operationId = "readArticle", summary = "Find article by Id", tags = {"Resource Controller"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Article Id")},
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = ArticleDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid article Id supplied"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Article not found",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class)))},
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/record/article/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<ArticleDto> readArticle(@PathVariable("id") UUID id) {
        log.debug("readArticle({})", id);
        return articleService.read(id);
    }

    @Operation(
            operationId = "readNewsEntry", summary = "Find news entry by Id", tags = {"Resource Controller"},
            parameters = {@Parameter(in = ParameterIn.PATH, name = "id", description = "Article Id")},
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = NewsEntryDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid news entry Id supplied"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
                    @ApiResponse(responseCode = "404", description = "News entry not found",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class)))},
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/record/news-entry/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<NewsEntryDto> readNewsEntry(@PathVariable("id") UUID id) {
        log.debug("getNewsEntry({})", id);
        return newsEntryService.read(id);
    }

    @Operation(summary = "Get all records")
    @GetMapping(value = "/records")
    @PreAuthorize("permitAll() or hasPermission()")
    public Mono<Page<AllRecordView>> readRecords(
            @RequestParam("page") @Parameter(name = "page", required = true, example = "0") int page,
            @RequestParam("size") @Parameter(name = "size", required = true, example = "999") int size) {
        log.debug("readRecords()");
        return recordNewsEntryService.getRecords(page, size);
    }

    @Operation(
            operationId = "updateArticle", summary = "Update article record", tags = {"Resource Controller"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)},
            security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "/record/article")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> updateArticle(@RequestBody ArticleDto dto) {
        log.debug("updateArticle({}): authentication={}", dto, SecurityContextHolder.getContext().getAuthentication());
        return articleService.update(dto)
                .map(a -> getBody(a, HttpStatus.OK, "Updated"));
    }

    @Operation(
            operationId = "updateNewsEntry", summary = "Update news entry record", tags = {"Resource Controller"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "successful operation",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(schema = @Schema(implementation = ResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)},
            security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "/record/news-entry")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> updateNewsEntry(@RequestBody NewsEntryDto dto) {
        log.debug("updateNewsEntry({}): authentication={}", dto, SecurityContextHolder.getContext().getAuthentication());
        return newsEntryService.update(dto)
                .map(a -> getBody(a, HttpStatus.OK, "Updated"));
    }

    private ResponseEntity<?> getBody(DBUuidEntry a, HttpStatus ok, String message) {
        ResponseDto response = ResponseDto.builder()
                .message(message + ": " + a.getId())
                .timestamp(new Date(new java.util.Date().getTime()))
                .status("success")
                .build();
        return ResponseEntity.status(ok).body(response);
    }
}
