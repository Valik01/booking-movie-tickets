package com.issoft.cinemaapplication.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.issoft.cinemaapplication.dto.user.UserLoginDto;
import com.issoft.cinemaapplication.dto.user.UserResponseDto;
import com.issoft.cinemaapplication.model.SystemRole;
import com.issoft.cinemaapplication.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException {
        try {
            final UserLoginDto loginDto =
                    new ObjectMapper().readValue(request.getInputStream(), UserLoginDto.class);

            return this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword()));
        } catch (IOException e) {
            throw new RuntimeException(String.format("Could not read request. %s", e));
        }
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        final SecuredUser securedUser = (SecuredUser) authResult.getPrincipal();

        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(securedUser.getUsername());

        final String roleName = authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).findFirst().get();
        final String token = this.jwtProvider.createToken(userDetails.getUsername(), new SystemRole(roleName));
        response.setContentType("application/json");

        final UserResponseDto userResponseDto = new UserResponseDto(securedUser.getUsername(), token);
        response.getWriter().write(new Gson().toJson(userResponseDto));
        response.getWriter().flush();
    }
}
