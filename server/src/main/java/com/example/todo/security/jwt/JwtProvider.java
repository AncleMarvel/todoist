package com.example.todo.security.jwt;

import com.example.todo.entities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class JwtProvider {

	private final JwtProperties jwtProperties;
	private Key secretKey;
	private JwtParser jwtParser;

	@Autowired
	public JwtProvider(JwtProperties jwtProperties) {
		this.jwtProperties = jwtProperties;
	}

	@PostConstruct
	private void setupJwt() {
		secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
		jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
	}

	public String generateRefreshToken(User user) {
		var date = getExpirationDateForToken(jwtProperties.getSecs_to_expire_refresh());
		return generateUserTokenWithExpirationDate(user, date);
	}

	public String generateAccessToken(User user) {
		var date = getExpirationDateForToken(jwtProperties.getSecs_to_expire_access());
		return generateUserTokenWithExpirationDate(user, date);
	}

	public String getLoginFromToken(String token) {
		var claims = parseToken(token);
		return claims.getSubject();
	}

	private String generateUserTokenWithExpirationDate(User user, LocalDateTime date) {
		return Jwts.builder()
				.setSubject(user.getEmail())
				.setExpiration(Date.from(date.toInstant(ZoneOffset.UTC)))
				.signWith(secretKey)
				.compact();
	}

	private LocalDateTime getExpirationDateForToken(Long secsToExpire) {
		return LocalDateTime.now().plusSeconds(secsToExpire);
	}

	private Claims parseToken(String token) {
		try {
			return jwtParser.parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException expEx) {
			throw new JwtException("Token expired", "jwt-expired");
		} catch (UnsupportedJwtException unsEx) {
			throw new JwtException("Unsupported jwt", "jwt-unsupported");
		} catch (MalformedJwtException mjEx) {
			throw new JwtException("Malformed jwt", "jwt-malformed");
		} catch (SignatureException sEx) {
			throw new JwtException("Invalid signature", "jwt-signature");
		} catch (Exception e) {
			throw new JwtException("Invalid token", "jwt-invalid");
		}
	}

}
