package com.samuelrogenes.clinicmanagement.services;

import com.samuelrogenes.clinicmanagement.dtos.CadastroDto;
import com.samuelrogenes.clinicmanagement.dtos.LoginDto;
import com.samuelrogenes.clinicmanagement.entities.UsuarioEntity;

public interface IUsuarioService {

	String login(LoginDto loginDto);
	UsuarioEntity cadastrar(CadastroDto cadastroDto);
	void solicitarAlteracaoSenha(String email);
	boolean verificarCodigo(String email, String codigo);
	void alterarSenha(String email, String novaSenha);
}
