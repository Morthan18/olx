package com.snokant.projekt.Configuration.JwtConfiguration;

import com.snokant.projekt.Model.Domain.UserDomain;
import com.snokant.projekt.Model.User;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author TTTDEMIRCI
 */


@Component
public class JwtGen {


    public String generateToken(User jwtUser) {
        Claims claims = Jwts.claims()
                .setExpiration(new Date(System.currentTimeMillis() + JwtConstants.EXPIRATION_TIME))
                .setSubject(jwtUser.getEmail());
        claims.put("email",jwtUser.getEmail());
        claims.put("password", jwtUser.getPassword());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, JwtConstants.SECRET)
                .compact();
    }
}
