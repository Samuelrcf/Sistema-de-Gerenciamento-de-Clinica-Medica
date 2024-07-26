package com.samuelrogenes.clinicmanagement.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.samuelrogenes.clinicmanagement.dtos.paciente.PacienteProjection;
import com.samuelrogenes.clinicmanagement.entities.PacienteEntity;

public interface PacienteRepository extends JpaRepository<PacienteEntity, Long> {

    @Query("SELECT p FROM PacienteEntity p WHERE p.email = :email OR p.telefone = :telefone OR p.cpf = :cpf OR p.rg = :rg")
    List<PacienteEntity> findConflictingPaciente(@Param("email") String email, @Param("telefone") String telefone, @Param("cpf") String cpf, @Param("rg") String rg);

    @Query("SELECT p.id AS id, p.nomeCompleto AS nomeCompleto, p.cpf AS cpf, p.sexo AS sexo, p.telefone AS telefone, "
         + "p.dataDeNascimento AS dataDeNascimento, p.observacoes AS observacoes "
         + "FROM PacienteEntity p WHERE p.id = :id")
    Optional<PacienteProjection> findPacienteById(@Param("id") Long id);

    @Query("SELECT p.id AS id, p.nomeCompleto AS nomeCompleto, p.cpf AS cpf, p.sexo AS sexo, p.telefone AS telefone, "
         + "p.dataDeNascimento AS dataDeNascimento, p.observacoes AS observacoes "
         + "FROM PacienteEntity p")
    Page<PacienteProjection> findAllPacientes(Pageable pageable);
}
