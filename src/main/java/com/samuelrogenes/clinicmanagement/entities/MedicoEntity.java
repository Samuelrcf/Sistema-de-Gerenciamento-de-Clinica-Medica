package com.samuelrogenes.clinicmanagement.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "medicos")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MedicoEntity extends UserEntity {

	private String conselhoMedico;
	private String UFConselho;
	private Integer numeroDoConselho;
	private String CBO;
}
