package com.samuelrogenes.clinicmanagement.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.samuelrogenes.clinicmanagement.dtos.CadastroDto;
import com.samuelrogenes.clinicmanagement.dtos.LoginDto;
import com.samuelrogenes.clinicmanagement.entities.UsuarioEntity;
import com.samuelrogenes.clinicmanagement.exceptions.ResourceAlreadyExistsException;
import com.samuelrogenes.clinicmanagement.projections.UsuarioProjection;
import com.samuelrogenes.clinicmanagement.repositories.UsuarioRepository;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLogin_UserNotFound() {
        LoginDto loginDto = new LoginDto("Samuel", "password");

        when(usuarioRepository.findByNome("Samuel")).thenReturn(null);

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            usuarioService.login(loginDto);
        });

        assertThat(exception.getMessage()).isEqualTo("O nome Samuel não existe.");
    }

    @Test
    public void testLogin_Success() {
        LoginDto loginDto = new LoginDto("Samuel", "password");
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome("Samuel");

        when(usuarioRepository.findByNome("Samuel")).thenReturn(usuarioEntity);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));
        when(tokenService.generateToken(any(UsuarioEntity.class))).thenReturn("mocked-token");

        String token = usuarioService.login(loginDto);

        assertThat(token).isEqualTo("mocked-token");
    }

    @Test
    public void testLogin_BadCredentials() {
        LoginDto loginDto = new LoginDto("Samuel", "wrong-password");

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setNome("Samuel");

        when(usuarioRepository.findByNome("Samuel")).thenReturn(usuarioEntity);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Senha incorreta. Tente novamente."));

        Exception exception = assertThrows(BadCredentialsException.class, () -> {
            usuarioService.login(loginDto);
        });

        assertThat(exception.getMessage()).isEqualTo("Senha incorreta. Tente novamente.");
    }

    @Test
    public void testCadastrar_NameAlreadyExists() {
        CadastroDto cadastroDto = new CadastroDto();
        cadastroDto.setNome("Samuel");

        when(usuarioRepository.findByNome("Samuel")).thenReturn(mock(UserDetails.class));

        Exception exception = assertThrows(ResourceAlreadyExistsException.class, () -> {
            usuarioService.cadastrar(cadastroDto);
        });

        assertThat(exception.getMessage()).isEqualTo("Não é possível cadastrar o nome Samuel porque ele já está em uso.");
    }

    @Test
    public void testCadastrar_EmailAlreadyExists() {
        CadastroDto cadastroDto = new CadastroDto();
        cadastroDto.setNome("Samuel");
        cadastroDto.setEmail("samuel@example.com");

        when(usuarioRepository.findByNome("Samuel")).thenReturn(null);
        when(usuarioRepository.findByEmail("samuel@example.com")).thenReturn(Optional.of(new UsuarioEntity()));

        Exception exception = assertThrows(ResourceAlreadyExistsException.class, () -> {
            usuarioService.cadastrar(cadastroDto);
        });

        assertThat(exception.getMessage()).isEqualTo("Não é possível cadastrar o email samuel@example.com porque ele já está em uso.");
    }

    @Test
    public void testCadastrar_Success() {
        CadastroDto cadastroDto = new CadastroDto();
        cadastroDto.setNome("Samuel");
        cadastroDto.setEmail("samuel@example.com");
        cadastroDto.setSenha("password");

        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(1L);
        usuarioEntity.setNome("Samuel");
        usuarioEntity.setEmail("samuel@example.com");

        UsuarioProjection usuarioProjection = mock(UsuarioProjection.class);
        when(usuarioProjection.getId()).thenReturn(1L);
        when(usuarioProjection.getNome()).thenReturn("Samuel");
        when(usuarioProjection.getEmail()).thenReturn("samuel@example.com");

        when(usuarioRepository.findByNome("Samuel")).thenReturn(null);
        when(usuarioRepository.findByEmail("samuel@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encoded-password");
        when(usuarioRepository.save(any(UsuarioEntity.class))).thenReturn(usuarioEntity);
        when(usuarioRepository.findUsuarioById(1L)).thenReturn(Optional.of(usuarioProjection));

        UsuarioProjection result = usuarioService.cadastrar(cadastroDto);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNome()).isEqualTo("Samuel");
        assertThat(result.getEmail()).isEqualTo("samuel@example.com");
    }
    
    @Test
    public void testSolicitarAlteracaoSenha() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("Samuel");
        usuario.setEmail("samuel@example.com");
        usuarioRepository.save(usuario);

        usuarioService.solicitarAlteracaoSenha("samuel@example.com");

        UsuarioEntity usuarioAtualizado = usuarioRepository.findByEmail("samuel@example.com").orElse(null);
        assertNotNull(usuarioAtualizado);
        assertNotNull(usuarioAtualizado.getCodigo());
        verify(emailService).enviarEmailVerificacao(eq("samuel@example.com"), anyString());
    }

    @Test
    public void testVerificarCodigo() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("Samuel");
        usuario.setEmail("samuel@example.com");
        String codigo = UUID.randomUUID().toString();
        usuario.setCodigo(codigo);
        usuarioRepository.save(usuario);

        boolean resultado = usuarioService.verificarCodigo("samuel@example.com", codigo);
        assertTrue(resultado);

        UsuarioEntity usuarioAtualizado = usuarioRepository.findByEmail("samuel@example.com").orElse(null);
        assertNotNull(usuarioAtualizado);
        assertNull(usuarioAtualizado.getCodigo());
    }

    @Test
    public void testVerificarCodigoInvalido() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("Samuel");
        usuario.setEmail("samuel@example.com");
        String codigo = UUID.randomUUID().toString();
        usuario.setCodigo(codigo);
        usuarioRepository.save(usuario);

        boolean resultado = usuarioService.verificarCodigo("samuel@example.com", "codigoInvalido");
        assertFalse(resultado);

        UsuarioEntity usuarioAtualizado = usuarioRepository.findByEmail("samuel@example.com").orElse(null);
        assertNotNull(usuarioAtualizado);
        assertNotNull(usuarioAtualizado.getCodigo());
    }

    @Test
    public void testAlterarSenha() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("Samuel");
        usuario.setEmail("samuel@example.com");
        usuario.setSenha("senhaAntiga");
        usuarioRepository.save(usuario);

        usuarioService.alterarSenha("samuel@example.com", "senhaNova");

        UsuarioEntity usuarioAtualizado = usuarioRepository.findByEmail("samuel@example.com").orElse(null);
        assertNotNull(usuarioAtualizado);
        assertTrue(passwordEncoder.matches("senhaNova", usuarioAtualizado.getSenha()));
    }

}
