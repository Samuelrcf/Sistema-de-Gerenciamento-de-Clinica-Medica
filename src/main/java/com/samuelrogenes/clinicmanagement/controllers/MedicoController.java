package com.samuelrogenes.clinicmanagement.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samuelrogenes.clinicmanagement.dtos.MedicoDto;
import com.samuelrogenes.clinicmanagement.entities.MedicoEntity;
import com.samuelrogenes.clinicmanagement.projections.MedicoProjection;
import com.samuelrogenes.clinicmanagement.services.IMedicoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/medicos")
@AllArgsConstructor
public class MedicoController {

	private final IMedicoService medicoService;

	@PostMapping
	public ResponseEntity<MedicoEntity> create(@Valid @RequestBody MedicoDto medicoDto) {
	    MedicoEntity medicoCriado = medicoService.create(medicoDto);
	    return new ResponseEntity<>(medicoCriado, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MedicoProjection> findById(@PathVariable Long id) {
		MedicoProjection medico = medicoService.findById(id);
		return ResponseEntity.ok(medico);
	}

	@GetMapping
	public ResponseEntity<Page<MedicoProjection>> findAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Page<MedicoProjection> medicos = medicoService.findAll(page, size);
		return ResponseEntity.ok(medicos);
	}

	@PutMapping("/{id}")
	public ResponseEntity<MedicoEntity> update(@PathVariable Long id, @Valid @RequestBody MedicoDto medicoDto) {
		MedicoEntity medicoAtualizado = medicoService.update(id, medicoDto);
		return ResponseEntity.ok(medicoAtualizado);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Long id) {
		boolean deleted = medicoService.deleteById(id);
		if (deleted) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
