package com.samuelrogenes.clinicmanagement.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "medicos")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MedicoEntity extends PessoaEntity {

	@Column(nullable = false)
	private String conselhoMedico;
	
	@Column(nullable = false)
	private String UFConselho;
	
	@Column(nullable = false)
	private Integer numeroDoConselho;
	
	private String CBO;
}
