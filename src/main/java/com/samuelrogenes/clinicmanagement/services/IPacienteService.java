package com.samuelrogenes.clinicmanagement.services;

import org.springframework.data.domain.Page;

import com.samuelrogenes.clinicmanagement.dtos.PacienteDto;
import com.samuelrogenes.clinicmanagement.entities.PacienteEntity;
import com.samuelrogenes.clinicmanagement.projections.PacienteProjection;

public interface IPacienteService {

    public PacienteEntity create(PacienteDto pacienteDto);
    public PacienteProjection findById(Long id);
    public Page<PacienteProjection> findAll(int page, int size);
    public PacienteEntity update(Long id, PacienteDto pacienteDto);
    public boolean deleteById(Long id);
}
