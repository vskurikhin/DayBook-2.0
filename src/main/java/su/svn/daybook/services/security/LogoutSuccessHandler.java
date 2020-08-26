package su.svn.daybook.services.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.savedrequest.ServerRequestCache;
import org.springframework.security.web.server.savedrequest.WebSessionServerRequestCache;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

public class LogoutSuccessHandler implements ServerLogoutSuccessHandler {
    private static final Logger LOG = LoggerFactory.getLogger(LogoutSuccessHandler.class);

    final static URI locationUsername = URI.create("/");

    private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();

    private ServerRequestCache requestCache = new WebSessionServerRequestCache();

    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        String name = authentication.getName();
        LOG.debug("authentication.getName(): {} logout", name);

        return this.requestCache.getRedirectUri(exchange)
                .defaultIfEmpty(locationUsername)
                .flatMap(location -> this.redirectStrategy.sendRedirect(exchange, location));
    }
}
