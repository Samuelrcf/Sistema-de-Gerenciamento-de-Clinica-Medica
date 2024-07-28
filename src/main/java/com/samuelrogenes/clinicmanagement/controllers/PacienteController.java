package com.samuelrogenes.clinicmanagement.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samuelrogenes.clinicmanagement.dtos.PacienteDto;
import com.samuelrogenes.clinicmanagement.entities.PacienteEntity;
import com.samuelrogenes.clinicmanagement.projections.PacienteProjection;
import com.samuelrogenes.clinicmanagement.services.IPacienteService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/pacientes")
@Validated
@AllArgsConstructor
public class PacienteController {

	private IPacienteService pacienteService;

	@PostMapping
	public ResponseEntity<PacienteEntity> createPaciente(@Valid @RequestBody PacienteDto pacienteDto) {
		PacienteEntity paciente = pacienteService.create(pacienteDto);
		return new ResponseEntity<>(paciente, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PacienteProjection> getPacienteById(@PathVariable Long id) {
		PacienteProjection paciente = pacienteService.findPacienteById(id);
		return new ResponseEntity<>(paciente, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Page<PacienteProjection>> getAllPacientes(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Page<PacienteProjection> pacientes = pacienteService.findAll(page, size);
		return new ResponseEntity<>(pacientes, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PacienteEntity> updatePaciente(@PathVariable Long id,
			@Valid @RequestBody PacienteDto pacienteDto) {
		PacienteEntity paciente = pacienteService.update(id, pacienteDto);
		return new ResponseEntity<>(paciente, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePaciente(@PathVariable Long id) {
		boolean deleted = pacienteService.deleteById(id);
		if (deleted) {
			return new ResponseEntity<>("Paciente excluído com sucesso.", HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
