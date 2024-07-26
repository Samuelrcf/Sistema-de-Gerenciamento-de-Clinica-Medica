package com.samuelrogenes.clinicmanagement.services;

import org.springframework.data.domain.Page;

import com.samuelrogenes.clinicmanagement.dtos.medico.MedicoDto;
import com.samuelrogenes.clinicmanagement.dtos.medico.MedicoProjection;

public interface IMedicoService {

	public MedicoProjection create(MedicoDto medicoDto);
	public MedicoProjection findById(Long id);
	public Page<MedicoProjection> findAll(int page, int size);
	public MedicoProjection update(Long id, MedicoDto medicoDto);
	public boolean deleteById(Long id);

}
