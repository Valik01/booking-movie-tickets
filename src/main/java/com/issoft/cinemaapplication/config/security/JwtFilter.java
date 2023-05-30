package com.issoft.cinemaapplication.config.security;

import com.issoft.cinemaapplication.exception.JwtAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        final String jwtToken = this.jwtProvider.resolveToken((HttpServletRequest) servletRequest);

        try {
            if (jwtToken != null && this.jwtProvider.validateToken(jwtToken)) {
                final Authentication auth = this.jwtProvider.getAuthentication(jwtToken);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (JwtAuthenticationException exception) {
            throw exception;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
