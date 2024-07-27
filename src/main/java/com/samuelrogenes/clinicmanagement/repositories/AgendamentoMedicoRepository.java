package com.samuelrogenes.clinicmanagement.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.samuelrogenes.clinicmanagement.dtos.agendamento.AgendamentoMedicoProjection;
import com.samuelrogenes.clinicmanagement.entities.AgendamentoMedicoEntity;

public interface AgendamentoMedicoRepository extends JpaRepository<AgendamentoMedicoEntity, Long> {

    @Query("SELECT a.id as id, a.medico.id as medicoId, a.paciente.id as pacienteId, "
           + "a.dataDaConsulta as dataDaConsulta, a.horaDaConsulta as horaDaConsulta, "
           + "a.observacoes as observacoes FROM AgendamentoMedicoEntity a WHERE a.id = :id")
    Optional<AgendamentoMedicoProjection> findAgendamentoById(@Param("id") Long id);

    @Query("SELECT a.id as id, a.medico.id as medicoId, a.paciente.id as pacienteId, "
           + "a.dataDaConsulta as dataDaConsulta, a.horaDaConsulta as horaDaConsulta, "
           + "a.observacoes as observacoes FROM AgendamentoMedicoEntity a")
    Page<AgendamentoMedicoProjection> findAllAgendamentos(Pageable pageable);

    @Query("SELECT a FROM AgendamentoMedicoEntity a WHERE a.medico.id = :medicoId "
           + "AND (a.dataDaConsulta = :inicioConsulta OR a.dataDaConsulta = :fimConsulta)")
    Optional<AgendamentoMedicoEntity> findAgendamentosByMedicoAndHorario(@Param("medicoId") Long medicoId,
                                                                          @Param("inicioConsulta") LocalDateTime inicioConsulta, 
                                                                          @Param("fimConsulta") LocalDateTime fimConsulta);

    @Query("SELECT a FROM AgendamentoMedicoEntity a WHERE a.medico.id = :medicoId "
           + "AND a.dataDaConsulta BETWEEN :inicioConsulta AND :fimConsulta "
           + "AND a.id <> :id")
    Optional<AgendamentoMedicoEntity> findAgendamentosByMedicoAndHorarioExceptId(@Param("medicoId") Long medicoId,
                                                                                 @Param("inicioConsulta") LocalDateTime inicioConsulta, 
                                                                                 @Param("fimConsulta") LocalDateTime fimConsulta,
                                                                                 @Param("id") Long id);

}
