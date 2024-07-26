package com.samuelrogenes.clinicmanagement.dtos.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

	@NotBlank(message = "Nome não pode ser em branco")
	private String nome;
	
	@NotBlank(message = "Senha não pode ser em branco")
	private String senha;
}
