package com.samuelrogenes.clinicmanagement.projections;

public interface MedicoProjection {
	
	Long getId();

	String getNomeCompleto();

	String getCpf();

	String getConselhoMedico();

	Integer getNumeroDoConselho();

	String getCBO();
}
