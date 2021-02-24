/*
 * This file was last modified at 2021.02.24 18:51 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ResourceController.java
 * $Id$
 */

package su.svn.daybook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.db.RecordNewsEntryService;
import su.svn.daybook.domain.model.NewsEntryDto;
import su.svn.daybook.domain.model.db.db.AllRecordView;
import su.svn.daybook.domain.security.ProfileResponse;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/resource")
@Tag(name = "Resource Controller", description = "Resource APIs for the DayBook project.")
public class ResourceController {

    private final RecordNewsEntryService recordNewsEntryService;

    public ResourceController(RecordNewsEntryService recordNewsEntryService) {
        this.recordNewsEntryService = recordNewsEntryService;
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

    @Operation(summary = "create news entry record", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/record/news-entry")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> createNewsEntryRecord(@RequestBody NewsEntryDto dto) {
        log.debug("createNewsEntryRecord({})", dto);
        log.debug("createNewsEntryRecord: authentication={}", SecurityContextHolder.getContext().getAuthentication());
        return recordNewsEntryService.insertNewsEntryNew(dto)
                .map(a -> ResponseEntity.status(HttpStatus.CREATED).body("Created: " + a.getId()));
    }

    @Operation(summary = "get news entry record by id")
    @GetMapping(value = "/record/news-entry/{id}")
    @PreAuthorize("permitAll() or hasPermission()")
    public Mono<NewsEntryDto> readNewsEntry(@PathVariable("id") UUID id) {
        log.debug("getNewsEntry({})", id);
        return recordNewsEntryService.getNewsEntry(id);
    }

    @Operation(summary = "get all records")
    @GetMapping(value = "/records")
    @PreAuthorize("permitAll() or hasPermission()")
    public Mono<Page<AllRecordView>> readRecords(
            @RequestParam("page") @Parameter(name = "page", required = true, example = "0") int page,
            @RequestParam("size") @Parameter(name = "size", required = true, example = "999") int size) {
        log.debug("readRecords()");
        return recordNewsEntryService.getRecords(page, size);
    }

    @Operation(summary = "update news entry record", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "/record/news-entry")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> updateNewsEntryRecord(@RequestBody NewsEntryDto dto) {
        log.debug("createNewsEntryRecord({})", dto);
        log.debug("createNewsEntryRecord: authentication={}", SecurityContextHolder.getContext().getAuthentication());
        return recordNewsEntryService.updateNewsEntryNew(dto)
                .map(a -> ResponseEntity.status(HttpStatus.OK).body("Updated: " + a.getId()));
    }
}
