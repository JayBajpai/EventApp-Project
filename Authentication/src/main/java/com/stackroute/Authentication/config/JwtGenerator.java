package com.stackroute.Authentication.config;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.stackroute.Authentication.entity.UserInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
public class JwtGenerator {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public Map<String, String> generateToken(UserInfo user) {
        Map<String, Object> userdata = new HashMap<>();
        userdata.put("username", user.getUsername());
        userdata.put("password", user.getPassword());

        String jwtToken = Jwts.builder()
                .setClaims(userdata)
                .setIssuedAt(new Date())
                .signWith(SECRET_KEY)
                .compact();

        Map<String, String> jwtTokenMap = new HashMap<>();
        jwtTokenMap.put("token", jwtToken);
        jwtTokenMap.put("message", "Login Successful");
        return jwtTokenMap;
    }

    public boolean verifyToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}