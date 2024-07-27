package com.samuelrogenes.clinicmanagement.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.samuelrogenes.clinicmanagement.dtos.usuario.CadastroDto;
import com.samuelrogenes.clinicmanagement.dtos.usuario.LoginDto;
import com.samuelrogenes.clinicmanagement.entities.UsuarioEntity;
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
    public ResponseEntity<UsuarioEntity> cadastrar(@RequestBody CadastroDto cadastroDto) {
        UsuarioEntity usuario = usuarioService.cadastrar(cadastroDto);
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @PostMapping("/solicitar-alteracao-senha")
    public ResponseEntity<Void> solicitarAlteracaoSenha(@RequestParam String email) {
        usuarioService.solicitarAlteracaoSenha(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/verificar-codigo")
    public ResponseEntity<Void> verificarCodigo(@RequestParam String email, @RequestParam String codigo) {
        boolean valido = usuarioService.verificarCodigo(email, codigo);
        if (valido) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/alterar-senha")
    public ResponseEntity<Void> alterarSenha(@RequestParam String email, @RequestParam String novaSenha) {
        usuarioService.alterarSenha(email, novaSenha);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
