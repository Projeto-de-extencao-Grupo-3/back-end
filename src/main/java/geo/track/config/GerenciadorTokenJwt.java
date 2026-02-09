package geo.track.config;

import geo.track.domain.Funcionario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GerenciadorTokenJwt {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.validity}")
    private long jwtTokenValidity;

    public String getUsernameFromToken(String token) {
        String username = getClaimForToken(token, Claims::getSubject);
        return username;
    }

    public Integer getIdOficinaFromToken(String token) {
        return getClaimForToken(token, claims -> claims.get("id_oficina", Integer.class));
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimForToken(token, Claims::getExpiration);
    }

    public String generateToken(final Authentication authentication, Funcionario funcionario) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("id_oficina", funcionario.getFkOficina().getIdOficina());


        // Para verificacoes de permissões;
        final String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String token = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(authentication.getName())
                .signWith(parseSecret()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtTokenValidity * 1_000)).compact();


        return token;
    }

    public <T> T getClaimForToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return (username.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date(System.currentTimeMillis()));
    }

    private Claims getAllClaimsFromToken(String token) {
        try {

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(parseSecret())
                    .build()
                    .parseClaimsJws(token.trim()).getBody();

            return claims;
        } catch (Exception e) {
            throw e;
        }
    }

    private SecretKey parseSecret() {
        SecretKey key = Keys.hmacShaKeyFor(this.secret.trim().getBytes(StandardCharsets.UTF_8));
        return key;
    }
}
