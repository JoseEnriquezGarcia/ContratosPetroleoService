package com.JEnriquez.Crud.Configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;

@Configuration
public class JWTAuthenticationConfig {

    private static final String SUPER_SECRET_KEY = "claveUltraSeguraCon64CaracteresExactosParaHS512____1234567890123456";
    private static final long TOKEN_EXPIRATION_TIME = 1000 * 60 * 60;

    public Map<String, String> getJWTToken(String username, Collection<? extends GrantedAuthority> authorities) {
        Map<String, String> result = new HashMap<>();

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME);

        String token = Jwts.builder()
                .setId("espinozajgeJWT")
                .setSubject(username)
                .claim("authorities", authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(getSigningKey(SUPER_SECRET_KEY), SignatureAlgorithm.HS512)
                .compact();

        result.put("token",token);
        result.put("expiration", expiration.toString());

        return result;
    }

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
