package su.svn.daybook.services.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.savedrequest.ServerRequestCache;
import org.springframework.security.web.server.savedrequest.WebSessionServerRequestCache;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

public class AuthenticationSuccessHandler extends RedirectServerAuthenticationSuccessHandler {
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);

    private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();

    private ServerRequestCache requestCache = new WebSessionServerRequestCache();

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange,
                                              Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        String name = authentication.getName();
        LOG.debug("authentication.getName(): {} login", name);
        final URI locationUsername = URI.create("/user/application?username=" + name);

        return this.requestCache.getRedirectUri(exchange)
                .defaultIfEmpty(locationUsername)
                .flatMap(location -> this.redirectStrategy.sendRedirect(exchange, location));
    }

    private String getClassName(Object o) {
        return o != null ? o.getClass().getName() : "null";
    }
}
