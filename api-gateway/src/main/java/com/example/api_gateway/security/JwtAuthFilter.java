package com.example.api_gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtAuthFilter implements GlobalFilter {

    @Value("${app.jwt.secret}")
    private String secret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        System.out.println("JWT FILTER HIT: " + path);

        // Guest (no token) allowed endpoints
        if (path.startsWith("/auth/")
                || path.startsWith("/actuator/")
                || path.startsWith("/diagnosis")
                || path.startsWith("/userprofile/register")
        ) {
            return chain.filter(exchange);
        }

        // Everything else requires token
        String auth = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (auth == null || !auth.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = auth.substring(7);

        try {
            Claims c = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String role = String.valueOf(c.get("role"));

            boolean registeredOnly =
                    path.startsWith("/userprofile/")
                            || path.startsWith("/bookmark/")
                            ||path.startsWith("/bookmark");

            if (registeredOnly && !"REGISTERED_USER".equalsIgnoreCase(role)) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            Object uid = c.get("userId");
            Object email = c.get("email");
            String sub = c.getSubject();

            ServerWebExchange ex2 = exchange.mutate()
                    .request(r -> r.headers(h -> {
                        if (uid != null) h.set("X-User-Id", String.valueOf(uid));
                        if (email != null) h.set("X-User-Email", String.valueOf(email));
                        if (sub != null) h.set("X-User-Sub", sub);
                        h.set("X-User-Role", role);
                    }))
                    .build();

            return chain.filter(ex2);

        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    private Key key() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


}
