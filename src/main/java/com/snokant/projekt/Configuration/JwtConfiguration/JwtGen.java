package com.snokant.projekt.Configuration.JwtConfiguration;
import com.snokant.projekt.Domain.User;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author TTTDEMIRCI
 *
 */


@Component
public class JwtGen {




    public String generate(User jwtUser) {


        Claims claims = Jwts.claims()
                .setSubject(jwtUser.getEmail());
        claims.put("role_id", jwtUser.getRole_id());


        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + 1800000))
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, JwtConstants.SECRET)
                .compact();
    }
}
