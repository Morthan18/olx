package com.snokant.projekt.Configuration.JwtConfiguration;


import com.snokant.projekt.Domain.User;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * @author TTTDEMIRCI
 *
 */
@Component
public class JwtVal {



    private String secret = "fenerbahce";

    public User validate(String token) {

        User jwtUser = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            jwtUser = new User();

            jwtUser.setEmail(body.getSubject());
            jwtUser.setUser_id(Integer.parseInt((String) body.get("userId")));
            jwtUser.setRole_id(Integer.parseInt((String) body.get("role")));
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return jwtUser;
    }
}
