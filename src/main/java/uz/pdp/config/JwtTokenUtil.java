package uz.pdp.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@PropertySource("classpath:application.properties")
public class JwtTokenUtil {

    private final SecretKey secretKey;

    private final int expirationTime;

    public JwtTokenUtil(@Value("${jwt.secret}") String key, @Value("${jwt.expiration}")  int expirationTime) {
        secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        this.expirationTime = expirationTime;
    }


    public String generateToken( @NonNull String username ) {
        return Jwts.builder()
                .subject(username)
                .issuedAt( new Date())
                .expiration( new Date( System.currentTimeMillis() + expirationTime)  )
                .signWith( secretKey )
                .compact();
    }


    public boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch ( Exception e ) {
            return false;
        }
    }

    public String getUsername(String token) {
            return getClaims(token).getSubject();
    }



    public Claims getClaims(String token) {
        return Jwts.parser().
                verifyWith( secretKey )
                .build()
                .parseSignedClaims( token )
                .getPayload();
    }


}
