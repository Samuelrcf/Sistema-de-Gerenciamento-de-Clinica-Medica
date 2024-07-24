package com.samuelrogenes.clinicmanagement.dtos.paciente;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreatePacienteDto {

    @NotBlank(message = "Nome completo não pode ser em branco")
    @Size(max = 100, message = "Nome completo deve ter no máximo 100 caracteres")
    private String nomeCompleto;

    @NotBlank(message = "CPF não pode ser em branco")
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos numéricos")
    private String cpf;

    @NotBlank(message = "Sexo não pode ser em branco")
    private String sexo;

    @NotBlank(message = "Logradouro não pode ser em branco")
    @Size(max = 255, message = "Logradouro deve ter no máximo 255 caracteres")
    private String logradouro;

    @NotBlank(message = "Bairro não pode ser em branco")
    @Size(max = 100, message = "Bairro deve ter no máximo 100 caracteres")
    private String bairro;

    @NotBlank(message = "Cidade não pode ser em branco")
    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    private String cidade;

    @NotBlank(message = "UF não pode ser em branco")
    @Pattern(regexp = "[A-Z]{2}", message = "UF deve ter 2 letras maiúsculas")
    private String uf;

    @NotBlank(message = "CEP não pode ser em branco")
    @Pattern(regexp = "\\d{8,9}", message = "CEP deve conter 8 dígitos numéricos") //dar replace no -
    private String cep;

    @NotBlank(message = "Telefone não pode ser em branco")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos numéricos")
    private String telefone;

    @NotBlank(message = "E-mail não pode ser em branco")
    @Email(message = "E-mail deve ser válido")
    private String email;

    @NotNull(message = "Data de nascimento não pode ser nula")
    private LocalDate dataDeNascimento;

    @NotBlank(message = "RG não pode ser em branco")
    @Size(max = 11, message = "RG deve conter 11 caracteres")
    private String RG;

    @NotBlank(message = "Órgão emissor não pode ser em branco")
    @Size(max = 50, message = "Órgão emissor deve ter no máximo 50 caracteres")
    private String orgaoEmissor;

    @Size(max = 500, message = "Observações deve ter no máximo 500 caracteres")
    private String observacoes;
}
