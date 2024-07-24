package com.samuelrogenes.clinicmanagement.entities;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "agendamentos")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AgendamentoMedicoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private MedicoEntity medico;
	private PacienteEntity paciente;
	private String motivoDaConsulta;
	private LocalDate dataDaConsulta;
	private LocalDate horaDaConsulta;
	private String local;
	private String observacoes;
}
