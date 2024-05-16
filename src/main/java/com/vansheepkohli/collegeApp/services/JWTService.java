package com.vansheepkohli.collegeApp.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {
    private final String SECRET_KEY = "edf27a7ce917b1c050d55ffa232a5f36cd6030abba3faea543b2b11bf48f97ad";

    public String extractUserName(String token){
        String xyz =  extractClaim(token, Claims::getSubject);
        System.out.println("inside extract username");
        System.out.println(xyz);
        return xyz;
    }

    public boolean isValid(String token, UserDetails user) {
        System.out.println(token + " --- " + user);
        String username = extractUserName(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        System.out.println("inside extract claim");
        System.out.println(claims);
        System.out.println(resolver.apply(claims));
        return resolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        System.out.println("inside extract all claims");
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

// 15*60*1000

    public String generateToken(String username){
        String token = Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1*60*1000))
                .signWith(getSignInKey())
                .compact();
        return token;
    }


    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
