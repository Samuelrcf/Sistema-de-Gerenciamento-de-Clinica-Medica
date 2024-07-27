package com.samuelrogenes.clinicmanagement.mapper;

import com.samuelrogenes.clinicmanagement.dtos.medico.MedicoDto;
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
	
}
