package com.samuelrogenes.clinicmanagement.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samuelrogenes.clinicmanagement.dtos.usuario.CadastroDto;
import com.samuelrogenes.clinicmanagement.dtos.usuario.LoginDto;
import com.samuelrogenes.clinicmanagement.dtos.usuario.UsuarioProjection;
import com.samuelrogenes.clinicmanagement.exceptions.ResourceAlreadyExistsException;
import com.samuelrogenes.clinicmanagement.services.IUsuarioService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final IUsuarioService iUsuarioService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        try {
            String token = iUsuarioService.login(loginDto);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (UsernameNotFoundException | BadCredentialsException | InternalAuthenticationServiceException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
    
    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioProjection> cadastrar(@RequestBody CadastroDto cadastroDto) {
        try {
            UsuarioProjection usuario = iUsuarioService.cadastrar(cadastroDto);
            return new ResponseEntity<>(usuario, HttpStatus.CREATED);
        } catch (ResourceAlreadyExistsException ex) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

}
