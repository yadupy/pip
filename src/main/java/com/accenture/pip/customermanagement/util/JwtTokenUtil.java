/*
package com.accenture.pip.customermanagement.util;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;

@Component
//@AllArgsConstructor
public class JwtTokenUtil {

    private final String secret_key = Jwts.SIG.HS256.key().toString();
    private final long accessTokenValidity = 60*60*1000;

    @Value("${security.jwt.token-secret-key}")
    private String secretKey;

    private Key secretKeySpec;
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";


    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        secretKeySpec = new SecretKeySpec(secretKey.getBytes(),SignatureAlgorithm.HS256.getJcaName());
    }
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        return createToken(userDetails.getUsername());
    }

    private String createToken(String username) {
        Claims claims = Jwts.claims()
                .subject(username)
                .build();

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiresAt = new Date(issuedAt.getTime()+accessTokenValidity);
        return Jwts.builder()
                .claims(claims)
                .issuedAt(issuedAt)
                .expiration(expiresAt)
                .signWith(secretKeySpec,SignatureAlgorithm.HS256)
                .compact();

    }

    private Claims parseJwtClaims(String token) {
        JwtParser parser = Jwts.parser().
                setSigningKey(secretKeySpec)
                .build();
        return parser.parseSignedClaims(token)
                .getPayload();

    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }

    public String getEmail(Claims claims) {
        return claims.getSubject();
    }

    private List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }
}
*/
