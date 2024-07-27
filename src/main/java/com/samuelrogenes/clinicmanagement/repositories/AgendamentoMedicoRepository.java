package com.samuelrogenes.clinicmanagement.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.samuelrogenes.clinicmanagement.entities.AgendamentoMedicoEntity;
import com.samuelrogenes.clinicmanagement.projections.AgendamentoMedicoProjection;

public interface AgendamentoMedicoRepository extends JpaRepository<AgendamentoMedicoEntity, Long> {

	@Query("SELECT a.id as id, a.medico.id as medicoId, a.paciente.id as pacienteId, "
			+ "a.dataDaConsulta as dataDaConsulta, a.horaDaConsulta as horaDaConsulta, "
			+ "a.observacoes as observacoes FROM AgendamentoMedicoEntity a WHERE a.id = :id")
	Optional<AgendamentoMedicoProjection> findAgendamentoById(@Param("id") Long id);

	@Query("SELECT a.id as id, a.medico.id as medicoId, a.paciente.id as pacienteId, "
			+ "a.dataDaConsulta as dataDaConsulta, a.horaDaConsulta as horaDaConsulta, "
			+ "a.observacoes as observacoes FROM AgendamentoMedicoEntity a")
	Page<AgendamentoMedicoProjection> findAllAgendamentos(Pageable pageable);

	@Query("SELECT a FROM AgendamentoMedicoEntity a WHERE a.dataDaConsulta = :dataConsulta "
			+ "AND ((a.horaDaConsulta BETWEEN :inicioHoraMin AND :fimHoraMax) "
			+ "OR (a.horaDaConsulta BETWEEN :inicioHoraMax AND :fimHoraMax) "
			+ "OR (a.horaDaConsulta <= :inicioHoraMax AND a.horaDaConsulta >= :inicioHoraMin))")
	List<AgendamentoMedicoEntity> findAgendamentosNoHorario(@Param("dataConsulta") LocalDate dataConsulta,
			@Param("inicioHoraMin") LocalTime inicioHoraMin, @Param("fimHoraMax") LocalTime fimHoraMax,
			@Param("inicioHoraMax") LocalTime inicioHoraMax);

	@Query("SELECT a FROM AgendamentoMedicoEntity a WHERE a.dataDaConsulta = :dataConsulta "
			+ "AND ((a.horaDaConsulta BETWEEN :inicioHoraMin AND :fimHoraMax) "
			+ "OR (a.horaDaConsulta BETWEEN :inicioHoraMax AND :fimHoraMax) "
			+ "OR (a.horaDaConsulta <= :inicioHoraMax AND a.horaDaConsulta >= :inicioHoraMin)) " + "AND a.id <> :id")
	List<AgendamentoMedicoEntity> findAgendamentosByHorarioExceptId(@Param("dataConsulta") LocalDate dataConsulta,
			@Param("inicioHoraMin") LocalTime inicioHoraMin, @Param("fimHoraMax") LocalTime fimHoraMax,
			@Param("inicioHoraMax") LocalTime inicioHoraMax, @Param("id") Long id);

}
