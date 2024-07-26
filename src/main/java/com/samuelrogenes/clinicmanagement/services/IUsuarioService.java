package com.samuelrogenes.clinicmanagement.services;

import com.samuelrogenes.clinicmanagement.dtos.usuario.CadastroDto;
import com.samuelrogenes.clinicmanagement.dtos.usuario.LoginDto;
import com.samuelrogenes.clinicmanagement.dtos.usuario.UsuarioProjection;

public interface IUsuarioService {

	String login(LoginDto loginDto);
	UsuarioProjection cadastrar(CadastroDto cadastroDto);
}
