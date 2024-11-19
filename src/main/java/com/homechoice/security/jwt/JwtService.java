package com.homechoice.security.jwt;

import com.homechoice.model.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class responsible for creating, validating, and extracting claims from JWT tokens.
 * It provides utility methods to generate a token based on user details, validate its authenticity,
 * and extract specific information such as the username and expiration date from the token.
 */
@Component
@RequiredArgsConstructor
public class JwtService {

    /**
     * The secret key used to sign the JWT token.
     */
    @Value("${jwt.secret-key}")
    private String key;

    /**
     * Generates a JWT token for the given user with extra claims.
     * The token contains the user's authorities, id, and username.
     *
     * @param user The user for whom the token is generated.
     * @return A JWT token as a String.
     */
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }

    /**
     * Generates a JWT token with the specified extra claims and user details.
     *
     * @param extraClaims Additional claims to include in the token.
     * @param user The user for whom the token is generated.
     * @return A JWT token as a String.
     */
    private String getToken(Map<String,Object> extraClaims, UserDetails user) {

        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        User customUser = (User) user;
        Integer id = customUser.getId();

        return Jwts
                .builder()
                .claims(extraClaims)
                .claim("authorities", authorities)
                .claim("id", id)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 6))
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * Retrieves the secret key used for signing the JWT token.
     *
     * @return The signing key as a SecretKey object.
     */
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts the username from the provided JWT token.
     *
     * @param token The JWT token.
     * @return The username extracted from the token.
     */
    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * Validates the JWT token by checking if it matches the username and is not expired.
     *
     * @param token The JWT token to validate.
     * @param userDetails The details of the user to compare the token against.
     * @return True if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token The JWT token.
     * @return The claims contained in the token.
     */
    private Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Retrieves a specific claim from the JWT token using a claim resolver.
     *
     * @param token The JWT token.
     * @param claimsResolver A function to resolve the claim from the JWT token.
     * @param <T> The type of the claim.
     * @return The claim extracted from the token.
     */
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token The JWT token.
     * @return The expiration date of the token.
     */
    public Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    /**
     * Checks if the JWT token has expired.
     *
     * @param token The JWT token.
     * @return True if the token has expired, false otherwise.
     */
    public boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }
}
