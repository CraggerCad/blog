package com.treeleaf.task.security.util;

import io.jsonwebtoken.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String getAudienceFromToken(String token){
        return getClaimFromToken(token, Claims::getAudience);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(tokenConstants.SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    public String generateToken(String userName, Collection<? extends GrantedAuthority> authorities) {
        return doGenerateToken(userName, authorities);
    }

    private String doGenerateToken(String subject, Collection<? extends GrantedAuthority> authorities) {

        Claims claims = Jwts.claims().setSubject(subject);
//        claims.put("scopes", authorities);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("system")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenConstants.ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS256, tokenConstants.SIGNING_KEY)
                .compact();
    }


    /*public String getAudienceFromToken(String token) {
        return getClaimFromToken(token, Claims::getAudience);
    }*/

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
