/*
<<<<<<< HEAD
 * This file was last modified at 2021.02.02 19:34 by Victor N. Skurikhin.
=======
 * This file was last modified at 2021.02.01 23:11 by Victor N. Skurikhin.
>>>>>>> e97099662bc293d42117c78731fbdd3ea84a0e76
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.Message;
import su.svn.daybook.domain.dao.db.db.RecordNewsEntryService;
import su.svn.daybook.domain.model.NewsEntryRecordDto;
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
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> profile(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            log.debug("User has authorities: {}", userDetails.getAuthorities());
        } else if (authentication.getPrincipal() instanceof String) {
            log.debug("User has authorities: {}", authentication.getAuthorities());
            return Mono.just(ResponseEntity.ok(new ProfileResponse(authentication.getPrincipal().toString())));
        }

        return Mono.just(ResponseEntity.ok(new Message("Content for user or admin")));
    }

    @Operation(summary = "create news entry record", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/record/news-entry")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> createNewsEntryRecord(@RequestBody NewsEntryRecordDto dto) {
        log.debug("createNewsEntryRecord({})", dto);
        return recordNewsEntryService.insertNewsEntry(dto.getRecord(), dto.getNewsEntry())
                .map(a -> ResponseEntity.status(HttpStatus.CREATED).body("Created: " + a.getId()));
    }

    @Operation(summary = "get news entry record by id")
    @GetMapping(value = "/record/fetch/{id}")
    @PreAuthorize("permitAll() or hasPermission()")
    public Mono<NewsEntryRecordDto> readNewsEntry(@PathVariable("id") UUID id) {
        log.debug("getNewsEntry({})", id);
        return recordNewsEntryService.getNewsEntryRecord(id);
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
}
