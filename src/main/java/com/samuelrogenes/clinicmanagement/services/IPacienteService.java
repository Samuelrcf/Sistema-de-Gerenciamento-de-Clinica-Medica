package com.samuelrogenes.clinicmanagement.services;

import org.springframework.data.domain.Page;

import com.samuelrogenes.clinicmanagement.dtos.paciente.PacienteDto;
import com.samuelrogenes.clinicmanagement.dtos.paciente.PacienteProjection;
import com.samuelrogenes.clinicmanagement.entities.PacienteEntity;

public interface IPacienteService {

    public PacienteEntity create(PacienteDto pacienteDto);
    public PacienteProjection findById(Long id);
    public Page<PacienteProjection> findAll(int page, int size);
    public PacienteEntity update(Long id, PacienteDto pacienteDto);
    public boolean deleteById(Long id);
}
