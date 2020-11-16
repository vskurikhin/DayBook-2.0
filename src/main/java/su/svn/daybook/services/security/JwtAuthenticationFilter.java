/*
 * This file was last modified at 2020.11.15 22:00 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * JwtAuthenticationFilter.java
 * $Id$
 */

package su.svn.daybook.services.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.server.savedrequest.ServerRequestCache;
import org.springframework.security.web.server.savedrequest.WebSessionServerRequestCache;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.Nonnull;

@Slf4j
public class JwtAuthenticationFilter implements WebFilter {

    public static final String AUTHORIZATION = "Authorization";
    private ServerRequestCache requestCache = new WebSessionServerRequestCache();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String jwtToken = getToken(exchange);
        log.debug("filter: jwtToken: {}", jwtToken);
        log.trace("filter: requestCache: {}", requestCache);
        return this.requestCache.removeMatchingRequest(exchange)
                .map(r -> exchange.mutate().request(r).build())
                .defaultIfEmpty(exchange)
                .flatMap(chain::filter);
    }

    public void setRequestCache(ServerRequestCache requestCache) {
        Assert.notNull(requestCache, "requestCache cannot be null");
        this.requestCache = requestCache;
    }

    private String getToken(@Nonnull ServerWebExchange exchange) {
        Object authorization = exchange.getRequest().getHeaders().get(AUTHORIZATION);
        if (authorization != null) {
            return authorization.toString();
        }
        log.error("ServerWebExchange: {}, Request: {}", exchange, exchange.getRequest());
        log.trace("ServerWebExchange: {}, Headers: {}", exchange, exchange.getRequest().getHeaders());

        return null;
    }
}
