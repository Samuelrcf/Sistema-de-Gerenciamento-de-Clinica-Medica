package com.samuelrogenes.clinicmanagement.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "agendamentos")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class AgendamentoMedicoEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "medico_id")
	private MedicoEntity medico;
	
	@ManyToOne
	@JoinColumn(name = "paciente_id")
	private PacienteEntity paciente;
	
	@Column(nullable = false)
	private String motivoDaConsulta;
	
	@Column(nullable = false)
	private LocalDate dataDaConsulta;
	
	@Column(nullable = false)
	private LocalTime horaDaConsulta;
	
	@Column(nullable = false)
	private String local;
	
	private String observacoes;
}
