package com.samuelrogenes.clinicmanagement.dtos.medico;

public interface MedicoProjection {
	
	Long getId();

	String getNomeCompleto();

	String getCpf();

	String getConselhoMedico();

	Integer getNumeroDoConselho();

	String getCBO();
}
