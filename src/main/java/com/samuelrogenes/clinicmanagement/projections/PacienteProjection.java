package com.samuelrogenes.clinicmanagement.projections;

import java.time.LocalDate;

public interface PacienteProjection {

    Long getId();

    String getNomeCompleto();

    String getCpf();

    String getSexo();

    String getTelefone();

    LocalDate getDataDeNascimento();

    String getObservacoes();
}
