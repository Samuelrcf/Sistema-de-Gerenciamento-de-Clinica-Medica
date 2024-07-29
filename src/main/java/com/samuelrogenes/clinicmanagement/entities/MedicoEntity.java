package com.samuelrogenes.clinicmanagement.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "medicos")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MedicoEntity extends PessoaEntity {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String conselhoMedico;
	
	@Column(nullable = false)
	private String UFConselho;
	
	@Column(nullable = false)
	private String numeroDoConselho;
	
	private String CBO;
	
	@JsonIgnore
	@OneToMany(mappedBy = "medico", cascade = CascadeType.REMOVE)
	private Set<AgendamentoMedicoEntity> agendamentoMedicoEntity;
}
