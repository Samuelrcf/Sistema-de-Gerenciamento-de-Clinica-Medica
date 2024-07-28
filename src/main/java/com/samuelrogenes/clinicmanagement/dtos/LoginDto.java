package com.samuelrogenes.clinicmanagement.dtos;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Login",
        description = "Schema para dados de login do usuário"
)
public class LoginDto {

    @NotBlank(message = "Nome não pode ser em branco")
    @Schema(
            description = "Nome do usuário que está tentando fazer login",
            example = "joao.silva"
    )
    private String nome;

    @NotBlank(message = "Senha não pode ser em branco")
    @Schema(
            description = "Senha do usuário para autenticação",
            example = "senhaSegura123"
    )
    private String senha;
}
