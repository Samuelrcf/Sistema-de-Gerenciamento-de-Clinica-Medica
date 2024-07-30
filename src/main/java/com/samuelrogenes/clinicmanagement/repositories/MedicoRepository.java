package com.samuelrogenes.clinicmanagement.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.samuelrogenes.clinicmanagement.entities.MedicoEntity;
import com.samuelrogenes.clinicmanagement.projections.MedicoProjection;

public interface MedicoRepository extends JpaRepository<MedicoEntity, Long> {
	
	Optional<MedicoEntity> findById(Long id); 

	@Query("SELECT m FROM MedicoEntity m WHERE m.email = :email OR m.telefone = :telefone OR m.cpf = :cpf")
	List<MedicoEntity> findConflictingMedico(@Param("email") String email, @Param("telefone") String telefone,
			@Param("cpf") String cpf);

	@Query("SELECT m.id AS id, m.nomeCompleto AS nomeCompleto, m.cpf AS cpf, m.conselhoMedico AS conselhoMedico, "
			+ "m.numeroDoConselho AS numeroDoConselho, m.CBO AS CBO FROM MedicoEntity m WHERE m.id = :id")
	Optional<MedicoProjection> findMedicoById(@Param("id") Long id);

	@Query("SELECT m.id AS id, m.nomeCompleto AS nomeCompleto, m.cpf AS cpf, m.conselhoMedico AS conselhoMedico, "
			+ "m.numeroDoConselho AS numeroDoConselho, m.CBO AS CBO FROM MedicoEntity m")
	Page<MedicoProjection> findAllMedicos(Pageable pageable);
	
	@Query("SELECT COUNT(p) > 0 FROM PacienteEntity p WHERE p.cpf = :cpf")
	boolean existsByCpfInPaciente(@Param("cpf") String cpf);

}
