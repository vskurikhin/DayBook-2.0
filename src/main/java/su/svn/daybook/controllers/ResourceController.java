/*
 * This file was last modified at 2021.01.12 21:43 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ResourceController.java
 * $Id$
 */

package su.svn.daybook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.Message;
import su.svn.daybook.domain.dao.db.db.RecordNewsEntryService;
import su.svn.daybook.domain.model.NewsEntryRecordDto;
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

    @Operation(summary = "user", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public Mono<ResponseEntity<?>> user() {
        return Mono.just(ResponseEntity.ok(new Message("Content for user")));
    }

    @Operation(summary = "admin", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> admin() {
        return Mono.just(ResponseEntity.ok(new Message("Content for admin")));
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

    @Operation(summary = "user or admin", security = @SecurityRequirement(name = "bearerAuth"))
    @RequestMapping(value = "/user-or-admin", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> userOrAdmin() {
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
    @GetMapping(value = "/record/news-entry/{id}")
    public Mono<NewsEntryRecordDto> readNewsEntry(@PathVariable("id") UUID id) {
        log.debug("getNewsEntry({})", id);
        return recordNewsEntryService.getNewsEntryRecord(id);
    }
}
