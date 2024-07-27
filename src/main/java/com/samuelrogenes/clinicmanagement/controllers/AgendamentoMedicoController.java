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

import com.samuelrogenes.clinicmanagement.dtos.AgendamentoMedicoDto;
import com.samuelrogenes.clinicmanagement.entities.AgendamentoMedicoEntity;
import com.samuelrogenes.clinicmanagement.projections.AgendamentoMedicoProjection;
import com.samuelrogenes.clinicmanagement.services.IAgendamentoMedicoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/agendamentos")
@Validated
@AllArgsConstructor
public class AgendamentoMedicoController {

    private final IAgendamentoMedicoService agendamentoService;

    @PostMapping
    public ResponseEntity<AgendamentoMedicoEntity> createAgendamento(@Valid @RequestBody AgendamentoMedicoDto agendamentoMedicoDto) {
    	AgendamentoMedicoEntity agendamento = agendamentoService.create(agendamentoMedicoDto);
        return new ResponseEntity<>(agendamento, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoMedicoProjection> getAgendamentoById(@PathVariable Long id) {
        AgendamentoMedicoProjection agendamento = agendamentoService.findById(id);
        return new ResponseEntity<>(agendamento, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<AgendamentoMedicoProjection>> getAllAgendamentos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AgendamentoMedicoProjection> agendamentos = agendamentoService.findAll(page, size);
        return new ResponseEntity<>(agendamentos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoMedicoEntity> updateAgendamento(@PathVariable Long id, @Valid @RequestBody AgendamentoMedicoDto agendamentoMedicoDto) {
    	AgendamentoMedicoEntity agendamento = agendamentoService.update(id, agendamentoMedicoDto);
        return new ResponseEntity<>(agendamento, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgendamento(@PathVariable Long id) {
        boolean deleted = agendamentoService.deleteById(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
