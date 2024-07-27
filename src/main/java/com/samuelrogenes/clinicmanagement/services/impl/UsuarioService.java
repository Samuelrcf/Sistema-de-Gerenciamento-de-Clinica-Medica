package com.samuelrogenes.clinicmanagement.services.impl;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.samuelrogenes.clinicmanagement.dtos.CadastroDto;
import com.samuelrogenes.clinicmanagement.dtos.LoginDto;
import com.samuelrogenes.clinicmanagement.entities.UsuarioEntity;
import com.samuelrogenes.clinicmanagement.exceptions.ResourceAlreadyExistsException;
import com.samuelrogenes.clinicmanagement.exceptions.ResourceNotFoundException;
import com.samuelrogenes.clinicmanagement.mapper.CadastroMapper;
import com.samuelrogenes.clinicmanagement.repositories.UsuarioRepository;
import com.samuelrogenes.clinicmanagement.services.IUsuarioService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService implements IUsuarioService{

	private UsuarioRepository usuarioRepository;
	private AuthenticationManager authenticationManager;
	private TokenService tokenService;
	private PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
	public String login(LoginDto loginDto) {
		UserDetails email = usuarioRepository.findByNome(loginDto.getNome());
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

    @Override
    public UsuarioEntity cadastrar(CadastroDto cadastroDto) {
        if (usuarioRepository.findByNome(cadastroDto.getNome()) != null) {
            throw new ResourceAlreadyExistsException(
                    "Não é possível cadastrar o nome " + cadastroDto.getNome() + " porque ele já está em uso.");
        }

        if (usuarioRepository.findByEmail(cadastroDto.getEmail()).isPresent()) {
            System.out.println("Email já está em uso: " + cadastroDto.getEmail());
            throw new ResourceAlreadyExistsException(
                    "Não é possível cadastrar o email " + cadastroDto.getEmail() + " porque ele já está em uso.");
        }

        UsuarioEntity usuarioMapeado = CadastroMapper.mapperToUsuarioEntity(new UsuarioEntity(), cadastroDto);

        String senhaCodificada = passwordEncoder.encode(cadastroDto.getSenha());
        usuarioMapeado.setSenha(senhaCodificada);

        UsuarioEntity usuarioSalvo = usuarioRepository.save(usuarioMapeado);

        return usuarioRepository.findById(usuarioSalvo.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Usuário com ID " + usuarioSalvo.getId() + " não encontrado"));
    }

	
    @Override
    public void solicitarAlteracaoSenha(String email) {
        UsuarioEntity usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Usuário com e-mail " + email + " não encontrado"));

        String codigoVerificacao = UUID.randomUUID().toString();
        usuario.setCodigo(codigoVerificacao);
        usuarioRepository.save(usuario);

        emailService.enviarEmailVerificacao(usuario.getEmail(), codigoVerificacao);
    }

    @Override
    public boolean verificarCodigo(String email, String codigo) {
    	UsuarioEntity usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Usuário com e-mail " + email + " não encontrado"));
        if (!usuario.getCodigo().equals(codigo)) {
            return false;
        }
        usuario.setCodigo(null);
        usuarioRepository.save(usuario);
        return true;
    }

    @Override
    public void alterarSenha(String email, String novaSenha) {
        UsuarioEntity usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Usuário com e-mail " + email + " não encontrado"));
        
        String senhaCodificada = passwordEncoder.encode(novaSenha);
        usuario.setSenha(senhaCodificada);
        usuarioRepository.save(usuario);
    }
}
