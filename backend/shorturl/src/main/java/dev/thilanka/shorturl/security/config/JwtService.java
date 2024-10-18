package dev.thilanka.shorturl.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "dfa2740015423f40da7388b4abed8adcd2c89e4e3b31a70ebbe59d1537d7aa2f3061fc8df5b2ff5ebac2b29a470c9e1fae9f7c42b91de951850c32a47d9a3111";
    private static final int EXPIRATION_TIME = 120;     //-- JWT expiration time in minutes

    //-- EXTRACTING CLAIMS --

    //-- Extract All claims from a JWT. Claim(s) are subject, issuer, issued_at, expires_at, etc.
    private Claims extractAllClaims(String jwt) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    //-- Extract a single claim using extractAllClaims method and by required claim as an argument
    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    //-- Extract Username from a JWT
    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    //-- Extract Expiration Date (time) from a JWT
    private Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }


    //-- KEY OPERATIONS --

    //-- Decode SECRET_KEY to Base64 to be included/signed the JWT
    private SecretKey getSignInKey() {
        byte[] secretKey = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secretKey);
    }


    //-- JWT OPERATIONS --

    //-- Generate new JWT including extra claims
    public String generateJwt(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME * 60 * 1000))
                .signWith(getSignInKey())
                .compact();
    }

    //-- Generate new JWT including only User details (Extra claims are null because empty HashMap)
    public String generateJwt(UserDetails userDetails) {
        return generateJwt(new HashMap<>(), userDetails);
    }

    //-- Checking validity of the JWT
    public boolean isJwtValid(String jwt, UserDetails userDetails) {
        final String username = extractUsername(jwt);
        return (username.equals(userDetails.getUsername())) && !isJwtExpired(jwt);
    }

    private boolean isJwtExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

}
