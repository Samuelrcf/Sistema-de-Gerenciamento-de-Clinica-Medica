package com.samuelrogenes.clinicmanagement.services;

import java.time.Instant;

import com.samuelrogenes.clinicmanagement.entities.UsuarioEntity;

public interface ITokenService {

	String generateToken(UsuarioEntity usuario);
	String validateToken(String token);
	Instant generateExpirationDate();
	
}
