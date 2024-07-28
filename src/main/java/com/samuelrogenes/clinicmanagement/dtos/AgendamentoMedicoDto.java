package com.samuelrogenes.clinicmanagement.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(
        name = "Agendamento Médico",
        description = "Schema para armazenar informações de agendamentos médicos"
)
public class AgendamentoMedicoDto {

    @NotNull(message = "Médico não pode ser nulo")
    @Schema(
            description = "ID do médico responsável pela consulta",
            example = "1"
    )
    private Long medicoId;

    @NotNull(message = "Paciente não pode ser nulo")
    @Schema(
            description = "ID do paciente agendado para a consulta",
            example = "2"
    )
    private Long pacienteId;

    @NotBlank(message = "Motivo da consulta não pode ser em branco")
    @Schema(
            description = "Motivo da consulta médica",
            example = "Dor de cabeça persistente"
    )
    private String motivoDaConsulta;

    @NotNull(message = "Data da consulta não pode ser nula")
    @Future(message = "Data da consulta deve ser no futuro")
    @Schema(
            description = "Data da consulta médica",
            example = "2024-08-01"
    )
    private LocalDate dataDaConsulta;

    @NotNull(message = "Hora da consulta não pode ser nula")
    @Schema(
            description = "Hora da consulta médica",
            example = "14:30"
    )
    private LocalTime horaDaConsulta;

    @NotBlank(message = "Local da consulta não pode ser em branco")
    @Schema(
            description = "Local onde a consulta será realizada",
            example = "Clínica Central, Sala 3"
    )
    private String local;

    @Size(max = 500, message = "Observações devem ter no máximo 500 caracteres")
    @Schema(
            description = "Observações adicionais sobre a consulta",
            example = "Paciente relata alergia a penicilina"
    )
    private String observacoes;
}
