package com.id.userproductservice.configuration;

import com.id.userproductservice.domain.UserResponse;
import com.id.userproductservice.services.CacheStore;
import com.id.userproductservice.services.UserImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
public class JwtConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(JwtConfiguration.class);
    public static final String KEY_CACHE_USER = "USER_TOKEN";

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${jwtExpired}")
    private int jwtExpired;

    @Autowired
    CacheStore cacheStore;

    public String generateJwtToken(Authentication authentication) {
        UserImpl userPrincipal = (UserImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpired))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String token) {
        boolean tokenValid = false;
        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);

            //validate by cache lifetime redis
            String userame = getUsernameFromJwtToken(token);
            UserResponse response = (UserResponse) cacheStore.getCache(KEY_CACHE_USER.concat(userame));
            if (!StringUtils.isEmpty(response)) {
                tokenValid = true;
            }
        } catch (Exception ex){
            logger.error("Error when validate token {}", ex);
        }
        return tokenValid;
    }
}

