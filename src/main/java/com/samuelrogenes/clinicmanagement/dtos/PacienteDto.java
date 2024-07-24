package com.samuelrogenes.clinicmanagement.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PacienteDto {
	
	private String nomeCompleto;
	private String cpf;
	private String sexo;
	private String logradouro;
	private String bairro;
	private String cidade;
	private String uf;
	private String cep;
	private String telefone;
	private String email;
	private LocalDate dataDeNascimento;
	private String RG;
	private String orgaoEmissor;
	private String observacoes;
}
