package com.samuelrogenes.clinicmanagement.services.impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.samuelrogenes.clinicmanagement.services.IEmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService implements IEmailService {

    private final JavaMailSender mailSender;

    @Override
    public void enviarEmailVerificacao(String email, String codigoVerificacao) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(email);
            helper.setSubject("Código de Verificação para Alteração de Senha");
            helper.setText("Seu código de verificação é: " + codigoVerificacao, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail de verificação", e);
        }
    }
}
