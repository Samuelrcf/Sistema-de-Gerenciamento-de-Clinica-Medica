package com.samuelrogenes.clinicmanagement.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samuelrogenes.clinicmanagement.dtos.CadastroDto;
import com.samuelrogenes.clinicmanagement.dtos.LoginDto;
import com.samuelrogenes.clinicmanagement.projections.UsuarioProjection;
import com.samuelrogenes.clinicmanagement.services.IUsuarioService;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @MockBean
    private IUsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    void testLogin() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setNome("Samuel");
        loginDto.setSenha("senha123");

        when(usuarioService.login(any(LoginDto.class))).thenReturn("mocked_token");

        mockMvc.perform(post("/api/usuarios/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("mocked_token"));
    }

    @Test
    void testCadastrar() throws Exception {
        CadastroDto cadastroDto = new CadastroDto();
        cadastroDto.setNome("Samuel");
        cadastroDto.setEmail("samuel@example.com");
        cadastroDto.setSenha("senha123");

        UsuarioProjection usuarioProjection = new UsuarioProjection() {
            @Override
            public Long getId() { return 1L; }
            @Override
            public String getNome() { return "Samuel"; }
            @Override
            public String getEmail() { return "samuel@example.com"; }
        };

        when(usuarioService.cadastrar(any(CadastroDto.class))).thenReturn(usuarioProjection);

        mockMvc.perform(post("/api/usuarios/cadastrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cadastroDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(usuarioProjection)));
    }

    @Test
    void testSolicitarAlteracaoSenha() throws Exception {
        String email = "samuel@example.com";

        mockMvc.perform(post("/api/usuarios/solicitar-alteracao-senha")
                .param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("Solicitação de alteração de senha enviada com sucesso."));
    }

    @Test
    void testVerificarCodigo() throws Exception {
        String email = "samuel@example.com";
        String codigo = "123456";

        when(usuarioService.verificarCodigo(eq(email), eq(codigo))).thenReturn(true);

        mockMvc.perform(post("/api/usuarios/verificar-codigo")
                .param("email", email)
                .param("codigo", codigo))
                .andExpect(status().isOk())
                .andExpect(content().string("Código de verificação válido."));
    }

    @Test
    void testAlterarSenha() throws Exception {
        String email = "samuel@example.com";
        String novaSenha = "novaSenha123";

        mockMvc.perform(post("/api/usuarios/alterar-senha")
                .param("email", email)
                .param("novaSenha", novaSenha))
                .andExpect(status().isOk())
                .andExpect(content().string("Senha alterada com sucesso."));
    }
}
