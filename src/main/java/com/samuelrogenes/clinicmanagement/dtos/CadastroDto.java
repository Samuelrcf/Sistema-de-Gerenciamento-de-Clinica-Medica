package com.samuelrogenes.clinicmanagement.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(
        name = "Cadastro",
        description = "Schema para armazenar informações de cadastro de usuário"
)
public class CadastroDto {

    @NotBlank(message = "Nome não pode ser em branco")
    @Schema(
            description = "Nome completo do usuário",
            example = "João Silva"
    )
    private String nome;

    @NotBlank(message = "Email não pode ser em branco")
    @Email(message = "E-mail deve ser válido")
    @Schema(
            description = "E-mail do usuário",
            example = "joao.silva@example.com"
    )
    private String email;

    @NotBlank(message = "Senha não pode ser em branco")
    @Schema(
            description = "Senha do usuário",
            example = "senha123"
    )
    private String senha;
}
