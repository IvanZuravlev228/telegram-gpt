package ivan.telegramgpt.security.jwt;

import io.jsonwebtoken.*;
import ivan.telegramgpt.model.entity.User;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import java.util.Base64;
import java.util.Date;
import java.util.Set;

@Component
public class JwtTokenProvider {
    private static final String AUTHENTICATION_TOKEN_TYPE = "Bearer ";
    private static final String REQUEST_HEADER = "Authorization";

    @Value("${secret.word}")
    private String secret;
    @Value("${validity.time}") // equal 60 min
    private long validityTime;
    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String username, Set<User.Role> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityTime);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(getUserNameFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails,
                "",
                userDetails.getAuthorities());
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(REQUEST_HEADER);
        if (token != null && token.startsWith(AUTHENTICATION_TOKEN_TYPE)) {
            return token.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return claims.getBody().getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Expire or invalid JWT token ", e);
        }
    }
}
