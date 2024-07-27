package com.samuelrogenes.clinicmanagement.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.samuelrogenes.clinicmanagement.services.IEmailService;

@Service
public class EmailService implements IEmailService {

    private final JavaMailSender mailSender;
    
	@Value("${spring.mail.username}")
	private String senderEmail;
	
	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
    @Override
    public void enviarEmailVerificacao(String email, String codigoVerificacao) {
        SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom(senderEmail);
		message.setTo(email);
		message.setSubject("Código de recuperação");
		message.setText("Aqui está seu código de recuperação da senha: " + codigoVerificacao);
		
		mailSender.send(message);
    }
}
