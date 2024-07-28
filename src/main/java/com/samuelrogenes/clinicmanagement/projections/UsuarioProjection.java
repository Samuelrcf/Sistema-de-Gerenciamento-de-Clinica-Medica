package com.samuelrogenes.clinicmanagement.projections;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "UsuarioProjection",
        description = "Projeção que representa as informações básicas de um usuário para visualização"
)
public interface UsuarioProjection {

    @Schema(description = "ID único do usuário", example = "1")
    Long getId();

    @Schema(description = "Nome do usuário", example = "João da Silva")
    String getNome();

    @Schema(description = "E-mail do usuário", example = "joao.silva@example.com")
    String getEmail();
}
