package com.samuelrogenes.clinicmanagement.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samuelrogenes.clinicmanagement.entities.AgendamentoMedicoEntity;

public interface AgendamentoMedicoRepository extends JpaRepository<AgendamentoMedicoEntity, UUID>{

}
