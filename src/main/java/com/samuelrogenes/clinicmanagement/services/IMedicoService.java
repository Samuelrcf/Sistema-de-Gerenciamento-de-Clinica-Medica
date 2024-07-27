package com.samuelrogenes.clinicmanagement.services;

import org.springframework.data.domain.Page;

import com.samuelrogenes.clinicmanagement.dtos.MedicoDto;
import com.samuelrogenes.clinicmanagement.entities.MedicoEntity;
import com.samuelrogenes.clinicmanagement.projections.MedicoProjection;

public interface IMedicoService {

	public MedicoEntity create(MedicoDto medicoDto);
	public MedicoProjection findById(Long id);
	public Page<MedicoProjection> findAll(int page, int size);
	public MedicoEntity update(Long id, MedicoDto medicoDto);
	public boolean deleteById(Long id);

}
