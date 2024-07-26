package com.samuelrogenes.clinicmanagement.mapper;

import com.samuelrogenes.clinicmanagement.dtos.usuario.CadastroDto;
import com.samuelrogenes.clinicmanagement.entities.UsuarioEntity;

public class CadastroMapper {

	public static UsuarioEntity mapperToUsuarioEntity(UsuarioEntity usuarioEntity, CadastroDto cadastroDto) {
		usuarioEntity.setEmail(cadastroDto.getEmail());
		usuarioEntity.setNome(cadastroDto.getNome());
		return usuarioEntity;
	}
}
