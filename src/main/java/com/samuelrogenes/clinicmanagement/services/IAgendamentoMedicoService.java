package com.samuelrogenes.clinicmanagement.services;

import org.springframework.data.domain.Page;

import com.samuelrogenes.clinicmanagement.dtos.AgendamentoMedicoDto;
import com.samuelrogenes.clinicmanagement.projections.AgendamentoMedicoProjection;

public interface IAgendamentoMedicoService {

	public AgendamentoMedicoDto create(AgendamentoMedicoDto agendamentoDto);
	public AgendamentoMedicoProjection findById(Long id);
	public Page<AgendamentoMedicoProjection> findAll(int page, int size);
	public AgendamentoMedicoDto update(Long id, AgendamentoMedicoDto agendamentoDto);
	public boolean deleteById(Long id);
    
}
