package com.samuelrogenes.clinicmanagement.services;

public interface IEmailService {
	public void enviarEmailVerificacao(String email, String codigoVerificacao);
}
