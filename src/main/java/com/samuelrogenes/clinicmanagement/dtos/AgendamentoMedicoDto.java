package com.samuelrogenes.clinicmanagement.dtos;

import java.time.LocalDate;

import com.samuelrogenes.clinicmanagement.entities.MedicoEntity;
import com.samuelrogenes.clinicmanagement.entities.PacienteEntity;

import lombok.Data;

@Data
public class AgendamentoMedicoDto {
	
	private MedicoEntity medico;
	private PacienteEntity paciente;
	private String motivoDaConsulta;
	private LocalDate dataDaConsulta;
	private LocalDate horaDaConsulta;
	private String local;
	private String observacoes;
}
