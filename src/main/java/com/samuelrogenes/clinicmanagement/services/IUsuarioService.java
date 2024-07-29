package com.samuelrogenes.clinicmanagement.services;

import com.samuelrogenes.clinicmanagement.dtos.CadastroDto;
import com.samuelrogenes.clinicmanagement.dtos.LoginDto;
import com.samuelrogenes.clinicmanagement.projections.UsuarioProjection;

public interface IUsuarioService {

	public String login(LoginDto loginDto);
	public UsuarioProjection cadastrar(CadastroDto cadastroDto);
	public void solicitarAlteracaoSenha(String email);
	public boolean verificarCodigo(String email, String codigo);
	public void alterarSenha(String email, String novaSenha);
}
