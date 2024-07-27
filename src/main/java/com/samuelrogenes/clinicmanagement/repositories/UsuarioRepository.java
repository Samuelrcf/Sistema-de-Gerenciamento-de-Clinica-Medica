package com.samuelrogenes.clinicmanagement.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import com.samuelrogenes.clinicmanagement.entities.UsuarioEntity;
import com.samuelrogenes.clinicmanagement.projections.UsuarioProjection;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

	UserDetails findByNome(String nome);
	
	Optional<UsuarioEntity> findByEmail(String email);
	
    @Query("SELECT u.id as id, u.nome as nome, u.email as email FROM UsuarioEntity u WHERE u.email = :email")
    UsuarioProjection findUsuarioProjectionByEmail(@Param("email") String email);

    @Query("SELECT u FROM UsuarioEntity u WHERE (u.email = :email OR u.nome = :nome) AND u.id <> :id")
    List<UsuarioEntity> findConflictingUsuario(@Param("email") String email, @Param("nome") String nome, @Param("id") Long id);

    @Query("SELECT u.id as id, u.nome as nome, u.email as email FROM UsuarioEntity u WHERE u.id = :id")
    Optional<UsuarioProjection> findUsuarioProjectionById(@Param("id") Long id);

    @Query(value = "SELECT u.id as id, u.nome as nome, u.email as email FROM UsuarioEntity u",
           countQuery = "SELECT count(u) FROM UsuarioEntity u")
    Page<UsuarioProjection> findAllUsuarios(Pageable pageable);

}
