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
import com.samuelrogenes.clinicmanagement.dtos.ErrorResponseDto;
import com.samuelrogenes.clinicmanagement.entities.AgendamentoMedicoEntity;
import com.samuelrogenes.clinicmanagement.projections.AgendamentoMedicoProjection;
import com.samuelrogenes.clinicmanagement.services.IAgendamentoMedicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(
        name = "APIs REST CRUD para Agendamentos Médicos na Gestão de Clínica",
        description = "APIs REST CRUD na Gestão de Clínica para CRIAR, ATUALIZAR, OBTER E EXCLUIR detalhes de agendamentos"
)
@RestController
@RequestMapping("api/agendamentos")
@Validated
@AllArgsConstructor
public class AgendamentoMedicoController {

    private final IAgendamentoMedicoService agendamentoService;

    @Operation(
            summary = "API REST para Criar Agendamento",
            description = "API REST para criar um novo Agendamento Médico na Gestão de Clínica"
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
    public ResponseEntity<AgendamentoMedicoEntity> createAgendamento(@Valid @RequestBody AgendamentoMedicoDto agendamentoMedicoDto) {
        AgendamentoMedicoEntity agendamento = agendamentoService.create(agendamentoMedicoDto);
        return new ResponseEntity<>(agendamento, HttpStatus.CREATED);
    }

    @Operation(
            summary = "API REST para Obter Detalhes do Agendamento",
            description = "API REST para obter detalhes do Agendamento Médico com base em um ID"
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
    public ResponseEntity<AgendamentoMedicoProjection> getAgendamentoById(@PathVariable Long id) {
        AgendamentoMedicoProjection agendamento = agendamentoService.findById(id);
        return new ResponseEntity<>(agendamento, HttpStatus.OK);
    }

    @Operation(
            summary = "API REST para Obter Todos os Agendamentos",
            description = "API REST para obter todos os Agendamentos Médicos com paginação"
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
    public ResponseEntity<Page<AgendamentoMedicoProjection>> getAllAgendamentos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AgendamentoMedicoProjection> agendamentos = agendamentoService.findAll(page, size);
        return new ResponseEntity<>(agendamentos, HttpStatus.OK);
    }

    @Operation(
            summary = "API REST para Atualizar Detalhes do Agendamento",
            description = "API REST para atualizar detalhes do Agendamento Médico com base em um ID"
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
    public ResponseEntity<AgendamentoMedicoEntity> updateAgendamento(@PathVariable Long id, @Valid @RequestBody AgendamentoMedicoDto agendamentoMedicoDto) {
        AgendamentoMedicoEntity agendamento = agendamentoService.update(id, agendamentoMedicoDto);
        return new ResponseEntity<>(agendamento, HttpStatus.OK);
    }

    @Operation(
            summary = "API REST para Excluir Agendamento",
            description = "API REST para excluir Agendamento Médico com base em um ID"
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
    public ResponseEntity<Void> deleteAgendamento(@PathVariable Long id) {
        boolean deleted = agendamentoService.deleteById(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
