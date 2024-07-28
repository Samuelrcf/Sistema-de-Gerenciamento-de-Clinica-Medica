package com.samuelrogenes.clinicmanagement.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samuelrogenes.clinicmanagement.dtos.CadastroDto;
import com.samuelrogenes.clinicmanagement.dtos.ErrorResponseDto;
import com.samuelrogenes.clinicmanagement.dtos.LoginDto;
import com.samuelrogenes.clinicmanagement.projections.UsuarioProjection;
import com.samuelrogenes.clinicmanagement.services.IUsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(
    name = "APIs REST para Usuários na Gestão de Clínica",
    description = "APIs REST na Gestão de Clínica para LOGIN, CADASTRO, SOLICITAR ALTERAÇÃO DE SENHA, VERIFICAR CÓDIGO E ALTERAR SENHA de usuários"
)
@RestController
@RequestMapping("/api/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final IUsuarioService usuarioService;

    @Operation(
        summary = "API REST para Login",
        description = "API REST para realizar login de um usuário na Gestão de Clínica"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "HTTP Status Unauthorized",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        String token = usuarioService.login(loginDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @Operation(
        summary = "API REST para Cadastro",
        description = "API REST para cadastrar um novo usuário na Gestão de Clínica"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "HTTP Status CREATED"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "HTTP Status BAD REQUEST",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    })
    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioProjection> cadastrar(@RequestBody CadastroDto cadastroDto) {
        UsuarioProjection usuario = usuarioService.cadastrar(cadastroDto);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @Operation(
        summary = "API REST para Solicitar Alteração de Senha",
        description = "API REST para solicitar a alteração de senha de um usuário na Gestão de Clínica"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "HTTP Status NOT FOUND",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    })
    @PostMapping("/solicitar-alteracao-senha")
    public ResponseEntity<String> solicitarAlteracaoSenha(@RequestParam String email) {
        usuarioService.solicitarAlteracaoSenha(email);
        return new ResponseEntity<>("Solicitação de alteração de senha enviada com sucesso.", HttpStatus.OK);
    }

    @Operation(
        summary = "API REST para Verificar Código de Alteração de Senha",
        description = "API REST para verificar o código de alteração de senha enviado para o email do usuário"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "HTTP Status BAD REQUEST",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "HTTP Status NOT FOUND",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    })
    @PostMapping("/verificar-codigo")
    public ResponseEntity<String> verificarCodigo(@RequestParam String email, @RequestParam String codigo) {
        boolean valido = usuarioService.verificarCodigo(email, codigo);
        if (valido) {
            return new ResponseEntity<>("Código de verificação válido.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Código de verificação inválido.", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
        summary = "API REST para Alterar Senha",
        description = "API REST para alterar a senha de um usuário na Gestão de Clínica"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "HTTP Status OK"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "HTTP Status NOT FOUND",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "HTTP Status Internal Server Error",
            content = @Content(
                schema = @Schema(implementation = ErrorResponseDto.class)
            )
        )
    })
    @PostMapping("/alterar-senha")
    public ResponseEntity<String> alterarSenha(@RequestParam String email, @RequestParam String novaSenha) {
        usuarioService.alterarSenha(email, novaSenha);
        return new ResponseEntity<>("Senha alterada com sucesso.", HttpStatus.OK);
    }
}
