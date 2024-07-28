package com.samuelrogenes.clinicmanagement.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

	@NotBlank(message = "Nome não pode ser em branco")
	private String nome;
	
	@NotBlank(message = "Senha não pode ser em branco")
	private String senha;
}
