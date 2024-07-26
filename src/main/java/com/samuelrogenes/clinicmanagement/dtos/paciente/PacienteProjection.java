package com.samuelrogenes.clinicmanagement.dtos.paciente;

import java.time.LocalDate;

public interface PacienteProjection {
	
	Long getId();

	String getNomeCompleto();

	String getCpf();

	String getSexo();

	String getTelefone();

	LocalDate getDataDeNascimento();

	String getObservacoes();
}
