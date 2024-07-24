package com.samuelrogenes.clinicmanagement.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "pacientes")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PacienteEntity extends UserEntity {

	private String sexo;
	private LocalDate dataDeNascimento;
	private String RG;
	private String orgaoEmissor;
	private String observacoes;
}
