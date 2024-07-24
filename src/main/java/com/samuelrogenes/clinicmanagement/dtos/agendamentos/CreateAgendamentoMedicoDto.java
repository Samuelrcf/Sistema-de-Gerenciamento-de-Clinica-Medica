package com.samuelrogenes.clinicmanagement.dtos.agendamentos;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAgendamentoMedicoDto {

    @NotNull(message = "Médico não pode ser nulo")
    private Long medicoId;

    @NotNull(message = "Paciente não pode ser nulo")
    private Long pacienteId;

    @NotBlank(message = "Motivo da consulta não pode ser em branco")
    private String motivoDaConsulta;

    @NotNull(message = "Data da consulta não pode ser nula")
    @Future(message = "Data da consulta deve ser no futuro")
    private LocalDate dataDaConsulta;

    @NotNull(message = "Hora da consulta não pode ser nula")
    private LocalTime horaDaConsulta;

    @NotBlank(message = "Local da consulta não pode ser em branco")
    private String local;

    @Size(max = 500, message = "Observações devem ter no máximo 500 caracteres")
    private String observacoes;
}
