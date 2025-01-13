package com.itsecasia.example.security.jwt;

import static com.itsecasia.example.constants.LogMessage.LOG_MESSAGE_INVALID_JWT_SIGNATURE;
import static com.itsecasia.example.constants.LogMessage.LOG_MESSAGE_INVALID_JWT_TOKEN;
import static com.itsecasia.example.constants.LogMessage.LOG_MESSAGE_JWT_CLAIMS_EMPTY;
import static com.itsecasia.example.constants.LogMessage.LOG_MESSAGE_JWT_TOKEN_EXPIRED;
import static com.itsecasia.example.constants.LogMessage.LOG_MESSAGE_JWT_TOKEN_UNSUPPORTED;

import java.security.PublicKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

	private PublicKey publicKey;

	public JwtUtil(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public String parseJwtFromHeader(String headerAuth) {
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(token).getPayload().getSubject();
	}

	public Claims getClaimsFromJwtToken(String token) {
		return Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(token).getPayload();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(authToken);
			return true;
		} catch (SignatureException e) {
			LOGGER.error(LOG_MESSAGE_INVALID_JWT_SIGNATURE, e.getMessage());
		} catch (MalformedJwtException e) {
			LOGGER.error(LOG_MESSAGE_INVALID_JWT_TOKEN, e.getMessage());
		} catch (ExpiredJwtException e) {
			LOGGER.error(LOG_MESSAGE_JWT_TOKEN_EXPIRED, e.getMessage());
		} catch (UnsupportedJwtException e) {
			LOGGER.error(LOG_MESSAGE_JWT_TOKEN_UNSUPPORTED, e.getMessage());
		} catch (IllegalArgumentException e) {
			LOGGER.error(LOG_MESSAGE_JWT_CLAIMS_EMPTY, e.getMessage());
		}

		return false;
	}

}
