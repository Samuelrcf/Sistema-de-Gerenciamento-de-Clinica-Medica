package com.samuelrogenes.clinicmanagement.services;

import org.springframework.data.domain.Page;

import com.samuelrogenes.clinicmanagement.dtos.PacienteDto;
import com.samuelrogenes.clinicmanagement.entities.PacienteEntity;
import com.samuelrogenes.clinicmanagement.projections.PacienteProjection;

public interface IPacienteService {

    public PacienteDto create(PacienteDto pacienteDto);
    public PacienteEntity findById(Long id);
    public PacienteProjection findPacienteById(Long id);
    public Page<PacienteProjection> findAll(int page, int size);
    public PacienteDto update(Long id, PacienteDto pacienteDto);
    public boolean deleteById(Long id);
}
