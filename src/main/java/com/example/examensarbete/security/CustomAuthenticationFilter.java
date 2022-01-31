package com.example.examensarbete.security;

import com.example.examensarbete.Model.User;
import com.example.examensarbete.security.dto.LoginResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * A filter that authenticates a user by name and password
 */

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JWTIssuer jwtIssuer;
    private AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, JWTIssuer jwtIssuer) {
        this.authenticationManager = authenticationManager;
        this.jwtIssuer = jwtIssuer;
    }

    /**
     * Checks if the attempted authentication is valid or not
     * @param request from request by user
     * @param response to send to user
     * @return UsernamePasswordAuthenticationToken object
     */
    @Override
    public org.springframework.security.core.Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        final String username = request.getParameter("username");
        final String password = request.getParameter("username");
        log.info("Authenticated user {}", username);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(token);
    }

    /**
     * Build a response, when authentications is successful
     * Sets the JWT-token inside a cookie, the header and the body of the response.
     * @param request from request by user
     * @param response to send to user
     * @param chain a list of filters
     * @param authResult the result of the authentication
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {

        User user = (User) authResult.getPrincipal();
        String token = jwtIssuer.generateToken(user);
        String formatCookie = "Bearer " + token;

        response.setHeader(HttpHeaders.AUTHORIZATION, formatCookie);
        response.addCookie(new Cookie("jwt_token",token));

        LoginResponseDto loginResponseDto = new LoginResponseDto(user.getUsername(),
                LoginResponseDto.convertSimpleGrantedAuthority((Collection<GrantedAuthority>) user.getAuthorities()),
                "Bearer " + token);

        response.setContentType(MimeTypeUtils.APPLICATION_JSON.getType());
        try {
            new ObjectMapper().writeValue(response.getOutputStream(), loginResponseDto);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Throws a exception with a message
     * @param request from request by user
     * @param response to send to user
     * @param failed authentication exception
     */
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed){
        log.info("unsuccessfulAuthentication {}", failed.getMessage());
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, failed.getMessage());
    }
}
