package com.samuelrogenes.clinicmanagement.services;

import org.springframework.data.domain.Page;

import com.samuelrogenes.clinicmanagement.dtos.agendamento.AgendamentoMedicoDto;
import com.samuelrogenes.clinicmanagement.dtos.agendamento.AgendamentoMedicoProjection;

public interface IAgendamentoMedicoService {

    AgendamentoMedicoProjection create(AgendamentoMedicoDto agendamentoDto);
    AgendamentoMedicoProjection findById(Long id);
    Page<AgendamentoMedicoProjection> findAll(int page, int size);
    AgendamentoMedicoProjection update(Long id, AgendamentoMedicoDto agendamentoDto);
    boolean deleteById(Long id);
}
