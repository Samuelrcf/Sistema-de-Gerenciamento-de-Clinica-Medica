package com.samuelrogenes.clinicmanagement.services;

import java.time.Instant;

import com.samuelrogenes.clinicmanagement.entities.UsuarioEntity;

public interface ITokenService {

	public String generateToken(UsuarioEntity usuario);
	public String validateToken(String token);
	public Instant generateExpirationDate();
	
}
