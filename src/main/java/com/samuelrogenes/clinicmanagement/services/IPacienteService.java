package com.samuelrogenes.clinicmanagement.services;

import org.springframework.data.domain.Page;

import com.samuelrogenes.clinicmanagement.dtos.paciente.PacienteDto;
import com.samuelrogenes.clinicmanagement.dtos.paciente.PacienteProjection;

public interface IPacienteService {

    public PacienteProjection create(PacienteDto pacienteDto);
    public PacienteProjection findById(Long id);
    public Page<PacienteProjection> findAll(int page, int size);
    public PacienteProjection update(Long id, PacienteDto pacienteDto);
    public boolean deleteById(Long id);
}
