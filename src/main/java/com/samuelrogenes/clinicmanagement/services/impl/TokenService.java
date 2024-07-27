package com.samuelrogenes.clinicmanagement.services.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.samuelrogenes.clinicmanagement.entities.UsuarioEntity;
import com.samuelrogenes.clinicmanagement.services.ITokenService;

@Service
public class TokenService implements ITokenService{

	@Value("${api.clinic.token.secret}")
	private String secret;
	
	@Override
	public String generateToken(UsuarioEntity usuario) throws JWTCreationException {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		String token = JWT.create()
				.withIssuer("clinic-api")
				.withSubject(usuario.getNome())
				.withExpiresAt(generateExpirationDate())
				.sign(algorithm);
		return token;
	}
	
	@Override
	public String validateToken(String token) throws JWTVerificationException{
		Algorithm algorithm = Algorithm.HMAC256(secret);
		return JWT.require(algorithm)
				.withIssuer("clinic-api")
				.build()
				.verify(token)
				.getSubject();
	}
	
	@Override
	public Instant generateExpirationDate() {
		return LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.of("-03:00"));
	}
}
