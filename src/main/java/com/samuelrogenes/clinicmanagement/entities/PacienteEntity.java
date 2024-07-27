package com.samuelrogenes.clinicmanagement.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "pacientes")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class PacienteEntity extends PessoaEntity {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String sexo;
	
	@Column(nullable = false)
	private LocalDate dataDeNascimento;
	
	@Column(nullable = false, unique = true)
	private String RG;
	
	@Column(nullable = false)
	private String orgaoEmissor;
	
	private String observacoes;
}
