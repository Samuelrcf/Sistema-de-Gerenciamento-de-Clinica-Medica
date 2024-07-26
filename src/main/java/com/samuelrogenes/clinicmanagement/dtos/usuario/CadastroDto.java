package com.samuelrogenes.clinicmanagement.dtos.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CadastroDto {

	@NotBlank(message = "Nome não pode ser em branco")
	private String nome;
	
	@NotBlank(message = "Email não pode ser em branco")
	@Email(message = "E-mail deve ser válido")
	private String email;
	
	@NotBlank(message = "Senha não pode ser em branco")
	private String senha;
}
