package com.samuelrogenes.clinicmanagement.projections;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AgendamentoMedicoProjection {

    Long getId();

    String getMedicoNomeCompleto();

    String getPacienteNomeCompleto();

    Long getPacienteId();

    LocalDate getDataDaConsulta();

    LocalTime getHoraDaConsulta();

    String getObservacoes();
}
