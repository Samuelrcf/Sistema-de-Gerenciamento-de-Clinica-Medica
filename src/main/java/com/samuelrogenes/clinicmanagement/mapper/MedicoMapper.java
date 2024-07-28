package com.samuelrogenes.clinicmanagement.mapper;

import com.samuelrogenes.clinicmanagement.dtos.MedicoDto;
import com.samuelrogenes.clinicmanagement.entities.MedicoEntity;

public class MedicoMapper {

	public static MedicoEntity mapperToMedicoEntity(MedicoEntity medicoEntity, MedicoDto medicoDto) {
		medicoEntity.setBairro(medicoDto.getBairro());
		medicoEntity.setCBO(medicoDto.getCbo());
		medicoEntity.setCep(medicoDto.getCep());
		medicoEntity.setCidade(medicoDto.getCidade());
		medicoEntity.setConselhoMedico(medicoDto.getConselhoMedico());
		medicoEntity.setCpf(medicoDto.getCpf());
		medicoEntity.setEmail(medicoDto.getEmail());
		medicoEntity.setLogradouro(medicoDto.getLogradouro());
		medicoEntity.setNomeCompleto(medicoDto.getNomeCompleto());
		medicoEntity.setNumeroDoConselho(medicoDto.getNumeroDoConselho());
		medicoEntity.setTelefone(medicoDto.getTelefone());
		medicoEntity.setUf(medicoDto.getUf());
		medicoEntity.setUFConselho(medicoDto.getUfConselho());
		return medicoEntity;
	}
	
   public static MedicoDto mapperToMedicoDto(MedicoEntity medicoEntity) {
        MedicoDto medicoDto = new MedicoDto();
        medicoDto.setBairro(medicoEntity.getBairro());
        medicoDto.setCbo(medicoEntity.getCBO());
        medicoDto.setCep(medicoEntity.getCep());
        medicoDto.setCidade(medicoEntity.getCidade());
        medicoDto.setConselhoMedico(medicoEntity.getConselhoMedico());
        medicoDto.setCpf(medicoEntity.getCpf());
        medicoDto.setEmail(medicoEntity.getEmail());
        medicoDto.setLogradouro(medicoEntity.getLogradouro());
        medicoDto.setNomeCompleto(medicoEntity.getNomeCompleto());
        medicoDto.setNumeroDoConselho(medicoEntity.getNumeroDoConselho());
        medicoDto.setTelefone(medicoEntity.getTelefone());
        medicoDto.setUf(medicoEntity.getUf());
        medicoDto.setUfConselho(medicoEntity.getUFConselho());
        return medicoDto;
    }
	
}
