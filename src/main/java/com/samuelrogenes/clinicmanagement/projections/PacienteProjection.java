package com.samuelrogenes.clinicmanagement.projections;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(
        name = "PacienteProjection",
        description = "Projeção que representa as informações básicas de um paciente para visualização"
)
public interface PacienteProjection {

    @Schema(description = "ID único do paciente", example = "1")
    Long getId();

    @Schema(description = "Nome completo do paciente", example = "Maria da Silva")
    String getNomeCompleto();

    @Schema(description = "CPF do paciente", example = "12345678901")
    String getCpf();

    @Schema(description = "Sexo do paciente", example = "Feminino")
    String getSexo();

    @Schema(description = "Telefone de contato do paciente", example = "11987654321")
    String getTelefone();

    @Schema(description = "Data de nascimento do paciente", example = "1985-05-20")
    LocalDate getDataDeNascimento();

    @Schema(description = "Observações adicionais sobre o paciente", example = "Paciente com histórico de alergias")
    String getObservacoes();
}
