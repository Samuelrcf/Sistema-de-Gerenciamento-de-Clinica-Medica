package com.samuelrogenes.clinicmanagement.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import com.samuelrogenes.clinicmanagement.entities.UsuarioEntity;
import com.samuelrogenes.clinicmanagement.projections.UsuarioProjection;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testFindByNome() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("Samuel");
        usuario.setEmail("samuel@example.com");
        usuarioRepository.save(usuario);

        UserDetails foundUser = usuarioRepository.findByNome("Samuel");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo("Samuel");
    }

    @Test
    public void testFindByEmail() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("Samuel");
        usuario.setEmail("samuel@example.com");
        usuarioRepository.save(usuario);

        Optional<UsuarioEntity> foundUser = usuarioRepository.findByEmail("samuel@example.com");
        assertTrue(foundUser.isPresent());
        assertThat(foundUser.get().getEmail()).isEqualTo("samuel@example.com");
    }

    @Test
    public void testFindConflictingUsuario() {
        UsuarioEntity usuario1 = new UsuarioEntity();
        usuario1.setNome("Samuel");
        usuario1.setEmail("samuel@example.com");
        usuarioRepository.save(usuario1);

        UsuarioEntity usuario2 = new UsuarioEntity();
        usuario2.setNome("John");
        usuario2.setEmail("john@example.com");
        usuarioRepository.save(usuario2);

        List<UsuarioEntity> conflictingUsers = usuarioRepository.findConflictingUsuario("samuel@example.com", "Samuel", usuario2.getId());
        assertThat(conflictingUsers).isNotEmpty();
    }

    @Test
    public void testFindUsuarioById() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome("Samuel");
        usuario.setEmail("samuel@example.com");
        usuarioRepository.save(usuario);

        Optional<UsuarioProjection> foundUser = usuarioRepository.findUsuarioById(usuario.getId());
        assertTrue(foundUser.isPresent());
        assertThat(foundUser.get().getEmail()).isEqualTo("samuel@example.com");
    }

    @Test
    public void testFindAllUsuarios() {
        UsuarioEntity usuario1 = new UsuarioEntity();
        usuario1.setNome("Samuel");
        usuario1.setEmail("samuel@example.com");
        usuarioRepository.save(usuario1);

        UsuarioEntity usuario2 = new UsuarioEntity();
        usuario2.setNome("John");
        usuario2.setEmail("john@example.com");
        usuarioRepository.save(usuario2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<UsuarioProjection> usuariosPage = usuarioRepository.findAllUsuarios(pageable);
        assertThat(usuariosPage.getTotalElements()).isEqualTo(2);
    }
}
