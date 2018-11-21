package com.snokant.projekt.Configuration.JwtConfiguration;
import com.snokant.projekt.Domain.User;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author TTTDEMIRCI
 *
 */


@Component
public class JwtGen {




    public String generate(User jwtUser) {


        Claims claims = Jwts.claims()
                .setSubject(jwtUser.getEmail());
        claims.put("userId", String.valueOf(jwtUser.getUser_id()));
        claims.put("role", jwtUser.getRole_id());


        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "fenerbahce")
                .compact();
    }
}
