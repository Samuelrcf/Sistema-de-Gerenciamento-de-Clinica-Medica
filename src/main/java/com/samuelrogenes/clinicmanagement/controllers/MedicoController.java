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
import com.samuelrogenes.clinicmanagement.dtos.MedicoDto;
import com.samuelrogenes.clinicmanagement.entities.MedicoEntity;
import com.samuelrogenes.clinicmanagement.projections.MedicoProjection;
import com.samuelrogenes.clinicmanagement.services.IMedicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(
        name = "APIs REST CRUD para Médicos na Gestão de Clínica",
        description = "APIs REST CRUD na Gestão de Clínica para CRIAR, ATUALIZAR, BUSCAR E EXCLUIR detalhes de médicos"
)
@RestController
@RequestMapping("/api/medicos")
@AllArgsConstructor
public class MedicoController {

    private final IMedicoService medicoService;

    @Operation(
            summary = "API REST para Criar Médico",
            description = "API REST para criar um novo médico na Gestão de Clínica"
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
    public ResponseEntity<MedicoEntity> create(@Valid @RequestBody MedicoDto medicoDto) {
        MedicoEntity medicoCriado = medicoService.create(medicoDto);
        return new ResponseEntity<>(medicoCriado, HttpStatus.CREATED);
    }

    @Operation(
            summary = "API REST para Obter Detalhes do Médico",
            description = "API REST para obter detalhes do médico com base em um ID"
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
    public ResponseEntity<MedicoProjection> findById(@PathVariable Long id) {
        MedicoProjection medico = medicoService.findMedicoById(id);
        return ResponseEntity.ok(medico);
    }

    @Operation(
            summary = "API REST para Obter Todos os Médicos",
            description = "API REST para obter todos os médicos com paginação"
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
    public ResponseEntity<Page<MedicoProjection>> findAll(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Page<MedicoProjection> medicos = medicoService.findAll(page, size);
        return ResponseEntity.ok(medicos);
    }

    @Operation(
            summary = "API REST para Atualizar Detalhes do Médico",
            description = "API REST para atualizar detalhes do médico com base em um ID"
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
    public ResponseEntity<MedicoEntity> update(@PathVariable Long id, @Valid @RequestBody MedicoDto medicoDto) {
        MedicoEntity medicoAtualizado = medicoService.update(id, medicoDto);
        return ResponseEntity.ok(medicoAtualizado);
    }

    @Operation(
            summary = "API REST para Excluir Médico",
            description = "API REST para excluir médico com base em um ID"
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
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        boolean deleted = medicoService.deleteById(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
