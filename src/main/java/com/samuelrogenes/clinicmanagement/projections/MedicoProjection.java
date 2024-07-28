package com.samuelrogenes.clinicmanagement.projections;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "MedicoProjection",
        description = "Projeção que representa as informações básicas de um médico para visualização"
)
public interface MedicoProjection {

    @Schema(description = "ID único do médico", example = "1")
    Long getId();

    @Schema(description = "Nome completo do médico", example = "Dr. João da Silva")
    String getNomeCompleto();

    @Schema(description = "CPF do médico", example = "12345678901")
    String getCpf();

    @Schema(description = "Conselho médico do médico", example = "CRM")
    String getConselhoMedico();

    @Schema(description = "Número do conselho do médico", example = "12345")
    Integer getNumeroDoConselho();

    @Schema(description = "Código Brasileiro de Ocupações (CBO) do médico", example = "12345")
    String getCBO();
}
