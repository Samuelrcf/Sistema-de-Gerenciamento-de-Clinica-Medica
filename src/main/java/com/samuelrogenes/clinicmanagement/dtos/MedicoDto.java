package com.samuelrogenes.clinicmanagement.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Medico",
        description = "Schema para dados do médico"
)
public class MedicoDto {

    @NotBlank(message = "Nome completo não pode ser em branco")
    @Size(max = 100, message = "Nome completo deve ter no máximo 100 caracteres")
    @Schema(
            description = "Nome completo do médico",
            example = "Dr. João da Silva"
    )
    private String nomeCompleto;

    @NotBlank(message = "CPF não pode ser em branco")
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos numéricos")
    @Schema(
            description = "Número do CPF do médico, composto por 11 dígitos numéricos",
            example = "12345678901"
    )
    private String cpf;

    @NotBlank(message = "Logradouro não pode ser em branco")
    @Size(max = 255, message = "Logradouro deve ter no máximo 255 caracteres")
    @Schema(
            description = "Endereço completo do médico",
            example = "Rua das Flores, 123"
    )
    private String logradouro;

    @NotBlank(message = "Bairro não pode ser em branco")
    @Size(max = 100, message = "Bairro deve ter no máximo 100 caracteres")
    @Schema(
            description = "Bairro onde o médico reside",
            example = "Jardim Botânico"
    )
    private String bairro;

    @NotBlank(message = "Cidade não pode ser em branco")
    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    @Schema(
            description = "Cidade onde o médico reside",
            example = "São Paulo"
    )
    private String cidade;

    @NotBlank(message = "UF não pode ser em branco")
    @Pattern(regexp = "[A-Z]{2}", message = "UF deve ser composta por 2 letras maiúsculas")
    @Schema(
            description = "Unidade Federativa (UF) onde o médico reside",
            example = "SP"
    )
    private String uf;

    @NotBlank(message = "CEP não pode ser em branco")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos numéricos")
    @Schema(
            description = "Código de Endereçamento Postal (CEP) do endereço do médico",
            example = "12345678"
    )
    private String cep;

    @NotBlank(message = "Telefone não pode ser em branco")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos numéricos")
    @Schema(
            description = "Número de telefone do médico, contendo 10 ou 11 dígitos",
            example = "11987654321"
    )
    private String telefone;

    @NotBlank(message = "E-mail não pode ser em branco")
    @Email(message = "E-mail deve ser válido")
    @Schema(
            description = "Endereço de e-mail do médico",
            example = "dr.joao.silva@email.com"
    )
    private String email;

    @NotBlank(message = "Conselho médico não pode ser em branco")
    @Schema(
            description = "Número do conselho médico ao qual o médico está registrado",
            example = "CRM-SP"
    )
    private String conselhoMedico;

    @NotBlank(message = "UF do conselho não pode ser em branco")
    @Size(max = 2, message = "UF do conselho deve ter no máximo 2 caracteres")
    @Schema(
            description = "Unidade Federativa (UF) do conselho médico",
            example = "SP"
    )
    private String ufConselho;

    @NotBlank(message = "Número do conselho não pode ser em branco")
    @Schema(
            description = "Número do registro do conselho médico",
            example = "123456"
    )
    private String numeroDoConselho;

    @Pattern(regexp = "\\d{5}", message = "CBO deve conter 5 caracteres")
    @Schema(
            description = "Código Brasileiro de Ocupações (CBO) do médico",
            example = "12345"
    )
    private String cbo;
}
