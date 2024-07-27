package com.samuelrogenes.clinicmanagement.mapper;

import com.samuelrogenes.clinicmanagement.dtos.paciente.PacienteDto;
import com.samuelrogenes.clinicmanagement.entities.PacienteEntity;

public class PacienteMapper {

    public static PacienteEntity mapperToPacienteEntity(PacienteEntity pacienteEntity, PacienteDto pacienteDto) {
        pacienteEntity.setNomeCompleto(pacienteDto.getNomeCompleto());
        pacienteEntity.setCpf(pacienteDto.getCpf());
        pacienteEntity.setSexo(pacienteDto.getSexo());
        pacienteEntity.setLogradouro(pacienteDto.getLogradouro());
        pacienteEntity.setBairro(pacienteDto.getBairro());
        pacienteEntity.setCidade(pacienteDto.getCidade());
        pacienteEntity.setUf(pacienteDto.getUf());
        pacienteEntity.setCep(pacienteDto.getCep());
        pacienteEntity.setTelefone(pacienteDto.getTelefone());
        pacienteEntity.setEmail(pacienteDto.getEmail());
        pacienteEntity.setDataDeNascimento(pacienteDto.getDataDeNascimento());
        pacienteEntity.setRG(pacienteDto.getRg());
        pacienteEntity.setOrgaoEmissor(pacienteDto.getOrgaoEmissor());
        pacienteEntity.setObservacoes(pacienteDto.getObservacoes());
        return pacienteEntity;
    }
}
