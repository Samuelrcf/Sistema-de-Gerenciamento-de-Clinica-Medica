package com.samuelrogenes.clinicmanagement.dtos.medico;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MedicoDto {

    @NotBlank(message = "Nome completo não pode ser em branco")
    @Size(max = 100, message = "Nome completo deve ter no máximo 100 caracteres")
    private String nomeCompleto;

    @NotBlank(message = "CPF não pode ser em branco")
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos numéricos")
    private String cpf;

    @NotBlank(message = "Logradouro não pode ser em branco")
    @Size(max = 255, message = "Logradouro deve ter no máximo 255 caracteres")
    private String logradouro;

    @NotBlank(message = "Bairro não pode ser em branco")
    @Size(max = 100, message = "Bairro deve ter no máximo 100 caracteres")
    private String bairro;

    @NotBlank(message = "Cidade não pode ser em branco")
    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    private String cidade;

    @NotBlank(message = "UF não pode ser em branco") //dar uppercase
    private String uf;

    @NotBlank(message = "CEP não pode ser em branco")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos numéricos") 
    private String cep;

    @NotBlank(message = "Telefone não pode ser em branco")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter 10 ou 11 dígitos numéricos")
    private String telefone;

    @NotBlank(message = "E-mail não pode ser em branco")
    @Email(message = "E-mail deve ser válido")
    private String email;

    @NotBlank(message = "Conselho médico não pode ser em branco")
    private String conselhoMedico;

    @NotBlank(message = "UF do conselho não pode ser em branco")
    @Size(max = 2, message = "UF do conselho deve ter no máximo 2 caracteres")
    private String ufConselho;

    @NotNull(message = "Número do conselho não pode ser nulo")
    private Integer numeroDoConselho;

    @Pattern(regexp = "\\d{5}", message = "CBO deve conter 5 caracteres") 
    private String cbo;
}
