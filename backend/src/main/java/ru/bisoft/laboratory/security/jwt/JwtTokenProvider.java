package ru.bisoft.laboratory.security.jwt;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ru.bisoft.laboratory.domain.auth.User;

@Component
public class JwtTokenProvider {

	@Value("${jwt.token.secret}")
	private String secret;
	@Value("${jwt.token.accessTokenTimeout}")
	private long accessTimeout;
	@Value("${jwt.token.refreshTokenTimeout}")
	private long refreshTimeout;

	private UserDetailsService userDetailsService;

	public String createToken(User user) {
		Claims claims = Jwts.claims().setSubject(user.getUsername());
		claims.put("roles", user.getGroups());
		Date now = new Date();
		Date validaty = new Date(now.getTime() + accessTimeout);
		return Jwts.builder().setClaims(claims).setExpiration(validaty).signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public Authentication getAuthentication(String token) {
		UserDetails user = userDetailsService.loadUserByUsername(getUsername(token));
		return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	}

	public String getUsername(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}

	public String resolveToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token != null && token.startsWith("Bearer ")) {
			return token.substring(7, token.length());
		}
		return null;
	}

	public boolean isValidToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			if (claims.getBody().getExpiration().before(new Date())) {
				return false;
			}
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			return false;
		}
	}

	public JwtTokenProvider(UserDetailsService userDetailsService) {
		super();
		this.userDetailsService = userDetailsService;
	}

	public long getAccessTimeout() {
		return accessTimeout;
	}

	public long getRefreshTimeout() {
		return refreshTimeout;
	}
}
