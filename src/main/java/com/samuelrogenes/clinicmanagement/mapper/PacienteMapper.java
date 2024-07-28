package com.samuelrogenes.clinicmanagement.mapper;

import com.samuelrogenes.clinicmanagement.dtos.PacienteDto;
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
    
    public static PacienteDto mapperToPacienteDto(PacienteEntity pacienteEntity) {
        PacienteDto pacienteDto = new PacienteDto();
        pacienteDto.setNomeCompleto(pacienteEntity.getNomeCompleto());
        pacienteDto.setCpf(pacienteEntity.getCpf());
        pacienteDto.setSexo(pacienteEntity.getSexo());
        pacienteDto.setLogradouro(pacienteEntity.getLogradouro());
        pacienteDto.setBairro(pacienteEntity.getBairro());
        pacienteDto.setCidade(pacienteEntity.getCidade());
        pacienteDto.setUf(pacienteEntity.getUf());
        pacienteDto.setCep(pacienteEntity.getCep());
        pacienteDto.setTelefone(pacienteEntity.getTelefone());
        pacienteDto.setEmail(pacienteEntity.getEmail());
        pacienteDto.setDataDeNascimento(pacienteEntity.getDataDeNascimento());
        pacienteDto.setRg(pacienteEntity.getRG());
        pacienteDto.setOrgaoEmissor(pacienteEntity.getOrgaoEmissor());
        pacienteDto.setObservacoes(pacienteEntity.getObservacoes());
        return pacienteDto;
    }
}
