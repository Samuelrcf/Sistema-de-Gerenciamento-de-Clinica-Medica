package com.samuelrogenes.clinicmanagement.services;

public interface IEmailService {
    void enviarEmailVerificacao(String email, String codigoVerificacao);
}
