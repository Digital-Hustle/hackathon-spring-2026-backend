package ru.core.profilems.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class HeaderAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        try {
            String userId = request.getHeader("X-User-Id");

            if (userId != null) {

                UserAuthentication authentication =
                        new UserAuthentication(UUID.fromString(userId), null);

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Authenticated user: {}", userId);

            } else {
                log.debug("No user headers found - unauthenticated request");
            }

        } catch (Exception exception) {
            log.warn("Failed to parse user headers: {}", exception.getMessage());
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }
}
