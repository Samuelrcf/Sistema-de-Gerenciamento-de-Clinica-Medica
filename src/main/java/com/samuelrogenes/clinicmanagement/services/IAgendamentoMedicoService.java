package com.samuelrogenes.clinicmanagement.services;

import org.springframework.data.domain.Page;

import com.samuelrogenes.clinicmanagement.dtos.AgendamentoMedicoDto;
import com.samuelrogenes.clinicmanagement.projections.AgendamentoMedicoProjection;

public interface IAgendamentoMedicoService {

    AgendamentoMedicoDto create(AgendamentoMedicoDto agendamentoDto);
    AgendamentoMedicoProjection findById(Long id);
    Page<AgendamentoMedicoProjection> findAll(int page, int size);
    AgendamentoMedicoDto update(Long id, AgendamentoMedicoDto agendamentoDto);
    boolean deleteById(Long id);
    
}
