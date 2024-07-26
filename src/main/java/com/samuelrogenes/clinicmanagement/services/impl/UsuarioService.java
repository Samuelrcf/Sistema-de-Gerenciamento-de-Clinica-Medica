package com.samuelrogenes.clinicmanagement.services.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.samuelrogenes.clinicmanagement.dtos.usuario.CadastroDto;
import com.samuelrogenes.clinicmanagement.dtos.usuario.LoginDto;
import com.samuelrogenes.clinicmanagement.dtos.usuario.UsuarioProjection;
import com.samuelrogenes.clinicmanagement.entities.UsuarioEntity;
import com.samuelrogenes.clinicmanagement.exceptions.ResourceAlreadyExistsException;
import com.samuelrogenes.clinicmanagement.exceptions.ResourceNotFoundException;
import com.samuelrogenes.clinicmanagement.mapper.CadastroMapper;
import com.samuelrogenes.clinicmanagement.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {

	private UsuarioRepository usuarioRepository;
	private AuthenticationManager authenticationManager;
	private TokenService tokenService;
	private PasswordEncoder passwordEncoder;

	public String login(LoginDto loginDto) {
		UserDetails email = usuarioRepository.findByUsername(loginDto.getNome());
		if (email == null) {
			throw new UsernameNotFoundException("O nome " + loginDto.getNome() + " não existe.");
		}
		try {
			UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
					loginDto.getNome(), loginDto.getSenha());
			Authentication auth = authenticationManager.authenticate(usernamePassword);
			UsuarioEntity usuario = (UsuarioEntity) auth.getPrincipal();
			String token = tokenService.generateToken(usuario);
			return token;
		} catch (BadCredentialsException ex) {
			throw new BadCredentialsException("Senha incorreta. Tente novamente.", ex);
		} catch (InternalAuthenticationServiceException ex) {
			throw new InternalAuthenticationServiceException("Erro interno de autenticação", ex);
		}
	}

	public UsuarioProjection cadastrar(CadastroDto cadastroDto) {
		// Verifica se o nome de usuário já está em uso
		if (usuarioRepository.findByUsername(cadastroDto.getNome()) != null) {
			throw new ResourceAlreadyExistsException(
					"Não é possível cadastrar o nome " + cadastroDto.getNome() + " porque ele já está em uso.");
		}

		// Verifica se o email já está em uso
		if (usuarioRepository.findUsuarioProjectionByEmail(cadastroDto.getEmail()) != null) {
			throw new ResourceAlreadyExistsException(
					"Não é possível cadastrar o email " + cadastroDto.getEmail() + " porque ele já está em uso.");
		}

		// Mapeia o DTO para a entidade
		UsuarioEntity usuarioMapeado = CadastroMapper.mapperToUsuarioEntity(new UsuarioEntity(), cadastroDto);
		
		String senhaCodificada = passwordEncoder.encode(cadastroDto.getSenha());
		usuarioMapeado.setSenha(senhaCodificada); // Define a senha codificada na entidade

		// Salva o usuário
		UsuarioEntity usuarioSalvo = usuarioRepository.save(usuarioMapeado);

		// Retorna a projeção do usuário salvo
		return usuarioRepository.findUsuarioById(usuarioSalvo.getId()).orElseThrow(
				() -> new ResourceNotFoundException("Usuário com ID " + usuarioSalvo.getId() + " não encontrado"));
	}
}
