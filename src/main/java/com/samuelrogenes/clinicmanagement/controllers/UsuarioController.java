package com.samuelrogenes.clinicmanagement.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samuelrogenes.clinicmanagement.dtos.CadastroDto;
import com.samuelrogenes.clinicmanagement.dtos.LoginDto;
import com.samuelrogenes.clinicmanagement.projections.UsuarioProjection;
import com.samuelrogenes.clinicmanagement.services.IUsuarioService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final IUsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        String token = usuarioService.login(loginDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
    
    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioProjection> cadastrar(@RequestBody CadastroDto cadastroDto) {
    	UsuarioProjection usuario = usuarioService.cadastrar(cadastroDto);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @PostMapping("/solicitar-alteracao-senha")
    public ResponseEntity<String> solicitarAlteracaoSenha(@RequestParam String email) {
        usuarioService.solicitarAlteracaoSenha(email);
        return new ResponseEntity<>("Solicitação de alteração de senha enviada com sucesso.", HttpStatus.OK);
    }

    @PostMapping("/verificar-codigo")
    public ResponseEntity<String> verificarCodigo(@RequestParam String email, @RequestParam String codigo) {
        boolean valido = usuarioService.verificarCodigo(email, codigo);
        if (valido) {
            return new ResponseEntity<>("Código de verificação válido.", HttpStatus.OK);
        } else {
        	return new ResponseEntity<>("Código de verificação inválido.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/alterar-senha")
    public ResponseEntity<String> alterarSenha(@RequestParam String email, @RequestParam String novaSenha) {
        usuarioService.alterarSenha(email, novaSenha);
        return new ResponseEntity<>("Senha alterada com sucesso.", HttpStatus.OK);
    }
}
