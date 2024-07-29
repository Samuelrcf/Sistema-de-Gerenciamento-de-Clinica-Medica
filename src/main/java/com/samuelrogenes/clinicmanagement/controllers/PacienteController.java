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

import com.samuelrogenes.clinicmanagement.dtos.ErrorResponseDto;
import com.samuelrogenes.clinicmanagement.dtos.PacienteDto;
import com.samuelrogenes.clinicmanagement.projections.PacienteProjection;
import com.samuelrogenes.clinicmanagement.services.IPacienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(
        name = "APIs REST CRUD para Pacientes na Gestão de Clínica",
        description = "APIs REST CRUD na Gestão de Clínica para CRIAR, ATUALIZAR, OBTER E EXCLUIR detalhes de pacientes"
)
@RestController
@RequestMapping("/api/pacientes")
@AllArgsConstructor
public class PacienteController {

    private final IPacienteService pacienteService;

    @Operation(
            summary = "API REST para Criar Paciente",
            description = "API REST para criar um novo Paciente na Gestão de Clínica"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<PacienteDto> createPaciente(@Valid @RequestBody PacienteDto pacienteDto) {
    	PacienteDto paciente = pacienteService.create(pacienteDto);
        return new ResponseEntity<>(paciente, HttpStatus.CREATED);
    }

    @Operation(
            summary = "API REST para Obter Detalhes do Paciente",
            description = "API REST para obter detalhes do Paciente com base em um ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status NOT FOUND",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<PacienteProjection> getPacienteById(@PathVariable Long id) {
        PacienteProjection paciente = pacienteService.findPacienteById(id);
        return new ResponseEntity<>(paciente, HttpStatus.OK);
    }

    @Operation(
            summary = "API REST para Obter Todos os Pacientes",
            description = "API REST para obter todos os Pacientes com paginação"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<Page<PacienteProjection>> getAllPacientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PacienteProjection> pacientes = pacienteService.findAll(page, size);
        return new ResponseEntity<>(pacientes, HttpStatus.OK);
    }

    @Operation(
            summary = "API REST para Atualizar Detalhes do Paciente",
            description = "API REST para atualizar detalhes do Paciente com base em um ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<PacienteDto> updatePaciente(@PathVariable Long id, @Valid @RequestBody PacienteDto pacienteDto) {
    	PacienteDto paciente = pacienteService.update(id, pacienteDto);
        return new ResponseEntity<>(paciente, HttpStatus.OK);
    }

    @Operation(
            summary = "API REST para Excluir Paciente",
            description = "API REST para excluir Paciente com base em um ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "HTTP Status NO CONTENT"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status NOT FOUND",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        boolean deleted = pacienteService.deleteById(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
