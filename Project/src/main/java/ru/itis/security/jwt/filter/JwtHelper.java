package ru.itis.security.jwt.filter;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.models.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtHelper {

    @Value("${jwt.secret}")
    private String secret;


    public boolean validateToken(String token) {
        Jws<Claims> claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    public String resolveToken(HttpServletRequest req) {
        String reqToken = req.getHeader("Authorization");
        String cookieToken = null;
        for (Cookie cookie : req.getCookies()) {
            if (cookie.getName().equals("Authorization")){
                cookieToken = cookie.getValue();
            }
        }
        if (reqToken != null) {
            return reqToken;
        }
        return cookieToken;
    }
}