/*
package com.accenture.pip.customermanagement.security.filter;

import com.accenture.pip.customermanagement.service.CustomerDetailsService;
import com.accenture.pip.customermanagement.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    public JwtTokenUtil jwtTokenUtil;

    @Autowired
    public CustomerDetailsService detailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String token = jwtTokenUtil.resolveToken(request);
        if(!StringUtils.isEmpty(token)){
            Claims claims = jwtTokenUtil.resolveClaims(request);
            if(claims!=null && jwtTokenUtil.validateClaims(claims)){
                String username = claims.getSubject();
                log.info("claim valdiated successfully for user: {}", username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(username,"",new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("user successfully authenticated, {}",username);
            }
        }
        filterChain.doFilter(request,response);
    }
}
*/
