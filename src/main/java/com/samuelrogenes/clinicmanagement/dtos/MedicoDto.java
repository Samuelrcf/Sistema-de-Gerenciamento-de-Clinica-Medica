package com.samuelrogenes.clinicmanagement.dtos;

import lombok.Data;

@Data
public class MedicoDto {
	
	private String nomeCompleto;
	private String cpf;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String uf;
	private String cep;
	private String telefone;
	private String email;
	private String conselhoMedico;
	private String UFConselho;
	private Integer numeroDoConselho;
	private String CBO;
}
