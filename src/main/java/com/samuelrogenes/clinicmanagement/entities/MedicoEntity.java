package com.samuelrogenes.clinicmanagement.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	private Integer numeroDoConselho;
	
	private String CBO;
}
