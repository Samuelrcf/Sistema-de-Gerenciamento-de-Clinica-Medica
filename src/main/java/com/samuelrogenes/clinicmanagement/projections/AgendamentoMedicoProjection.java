package com.samuelrogenes.clinicmanagement.projections;

import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "AgendamentoMedicoProjection",
        description = "Projeção que representa as informações de um agendamento médico para visualização"
)
public interface AgendamentoMedicoProjection {

    @Schema(description = "ID único do agendamento", example = "1")
    Long getId();

    @Schema(description = "Nome completo do médico associado ao agendamento", example = "Dr. João da Silva")
    String getMedicoNomeCompleto();

    @Schema(description = "Nome completo do paciente associado ao agendamento", example = "Maria Oliveira")
    String getPacienteNomeCompleto();

    @Schema(description = "ID do paciente associado ao agendamento", example = "123")
    Long getPacienteId();

    @Schema(description = "Data da consulta agendada", example = "2024-08-15")
    LocalDate getDataDaConsulta();

    @Schema(description = "Hora da consulta agendada", example = "10:30:00")
    LocalTime getHoraDaConsulta();

    @Schema(description = "Observações relacionadas ao agendamento", example = "Paciente tem alergia a penicilina")
    String getObservacoes();
}
