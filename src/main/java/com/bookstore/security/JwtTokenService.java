package com.bookstore.security;

import com.bookstore.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService implements com.bookstore.security.TokenService {

  private final String JWT_SECRET = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6" +
      "IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImp0aSI6ImYxZjE4NTBjLTQ4YTItNDI4ZS1hOTI5LTg1ODcyYjJiYzk3OSIsImlhdCI6M" +
      "TYwNTkwOTk0MCwiZXhwIjoxNjA1OTEzNTQwfQ.DWNIh84vFhtTO_rJfbWb663-H1YtZQqX4w1iE79o8m0";

  @Override
  public String generateToken(User user) {
    Instant expirationTime = Instant.now().plus(1, ChronoUnit.HOURS);
    Date expirationDate = Date.from(expirationTime);

    Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

    String compactTokenString = Jwts.builder()
        .claim("id", user.getId())
        .claim("sub", user.getUsername())
        .setExpiration(expirationDate)
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();

    return "Bearer " + compactTokenString;
  }

  @Override
  public com.bookstore.security.UserPrincipal parseToken(String token) {
    byte[] secretBytes = JWT_SECRET.getBytes();

    Jws<Claims> jwsClaims = Jwts.parserBuilder()
        .setSigningKey(secretBytes)
        .build()
        .parseClaimsJws(token);

    String username = jwsClaims.getBody()
        .getSubject();
    Integer userId = jwsClaims.getBody()
        .get("id", Integer.class);

    return new com.bookstore.security.UserPrincipal(userId, username);
  }
}
