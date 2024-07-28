package com.samuelrogenes.clinicmanagement.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "Paciente",
        description = "Schema para dados do paciente"
)
public class PacienteDto {

    @NotBlank(message = "Nome completo não pode ser em branco")
    @Size(max = 100, message = "Nome completo deve ter no máximo 100 caracteres")
    @Schema(
            description = "Nome completo do paciente",
            example = "Maria da Silva"
    )
    private String nomeCompleto;

    @NotBlank(message = "CPF não pode ser em branco")
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos numéricos")
    @Schema(
            description = "Número do CPF do paciente, composto por 11 dígitos numéricos",
            example = "12345678901"
    )
    private String cpf;

    @NotBlank(message = "Sexo não pode ser em branco")
    @Schema(
            description = "Sexo do paciente",
            example = "Feminino"
    )
    private String sexo;

    @NotBlank(message = "Logradouro não pode ser em branco")
    @Size(max = 255, message = "Logradouro deve ter no máximo 255 caracteres")
    @Schema(
            description = "Endereço do paciente",
            example = "Avenida Paulista, 1000"
    )
    private String logradouro;

    @NotBlank(message = "Bairro não pode ser em branco")
    @Size(max = 100, message = "Bairro deve ter no máximo 100 caracteres")
    @Schema(
            description = "Bairro onde o paciente reside",
            example = "Centro"
    )
    private String bairro;

    @NotBlank(message = "Cidade não pode ser em branco")
    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    @Schema(
            description = "Cidade onde o paciente reside",
            example = "São Paulo"
    )
    private String cidade;

    @NotBlank(message = "UF não pode ser em branco")
    @Pattern(regexp = "[A-Z]{2}", message = "UF deve ter 2 letras maiúsculas")
    @Schema(
            description = "Unidade Federativa (UF) onde o paciente reside",
            example = "SP"
    )
    private String uf;

    @NotBlank(message = "CEP não pode ser em branco")
    @Pattern(regexp = "\\d{8,9}", message = "CEP deve conter 8 ou 9 dígitos numéricos")
    @Schema(
            description = "Código de Endereçamento Postal (CEP) do endereço do paciente",
            example = "12345678"
    )
    private String cep;

    @NotBlank(message = "Telefone não pode ser em branco")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos numéricos")
    @Schema(
            description = "Número de telefone do paciente, contendo 10 ou 11 dígitos",
            example = "11987654321"
    )
    private String telefone;

    @NotBlank(message = "E-mail não pode ser em branco")
    @Email(message = "E-mail deve ser válido")
    @Schema(
            description = "Endereço de e-mail do paciente",
            example = "maria.silva@email.com"
    )
    private String email;

    @NotNull(message = "Data de nascimento não pode ser nula")
    @Schema(
            description = "Data de nascimento do paciente",
            example = "1985-03-15"
    )
    private LocalDate dataDeNascimento;

    @NotBlank(message = "RG não pode ser em branco")
    @Size(max = 11, message = "RG deve conter no máximo 11 caracteres")
    @Schema(
            description = "Número do RG do paciente",
            example = "1234567890"
    )
    private String rg;

    @NotBlank(message = "Órgão emissor não pode ser em branco")
    @Size(max = 50, message = "Órgão emissor deve ter no máximo 50 caracteres")
    @Schema(
            description = "Órgão emissor do RG",
            example = "SSP-SP"
    )
    private String orgaoEmissor;

    @Size(max = 500, message = "Observações deve ter no máximo 500 caracteres")
    @Schema(
            description = "Observações adicionais sobre o paciente",
            example = "Paciente tem histórico de alergias a medicamentos."
    )
    private String observacoes;
}
