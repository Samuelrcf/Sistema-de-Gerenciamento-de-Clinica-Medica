package com.samuelrogenes.clinicmanagement.services.impl;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.samuelrogenes.clinicmanagement.dtos.AgendamentoMedicoDto;
import com.samuelrogenes.clinicmanagement.entities.AgendamentoMedicoEntity;
import com.samuelrogenes.clinicmanagement.entities.MedicoEntity;
import com.samuelrogenes.clinicmanagement.entities.PacienteEntity;
import com.samuelrogenes.clinicmanagement.exceptions.ConflictException;
import com.samuelrogenes.clinicmanagement.exceptions.ResourceNotFoundException;
import com.samuelrogenes.clinicmanagement.mapper.AgendamentoMedicoMapper;
import com.samuelrogenes.clinicmanagement.projections.AgendamentoMedicoProjection;
import com.samuelrogenes.clinicmanagement.repositories.AgendamentoMedicoRepository;
import com.samuelrogenes.clinicmanagement.services.IAgendamentoMedicoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AgendamentoMedicoService implements IAgendamentoMedicoService {

    private AgendamentoMedicoRepository agendamentoMedicoRepository;
    private MedicoService medicoService;
    private PacienteService pacienteService;

    @Override
    public AgendamentoMedicoDto create(AgendamentoMedicoDto agendamentoDto) {
        MedicoEntity medico = medicoService.findById(agendamentoDto.getMedicoId());
        PacienteEntity paciente = pacienteService.findById(agendamentoDto.getPacienteId());

        validateHorario(agendamentoDto.getDataDaConsulta(), agendamentoDto.getHoraDaConsulta(), null);

        AgendamentoMedicoEntity agendamento = AgendamentoMedicoMapper.mapperToAgendamentoMedicoEntity(
                new AgendamentoMedicoEntity(), agendamentoDto, medico, paciente);
        AgendamentoMedicoEntity agendamentoSalvo = agendamentoMedicoRepository.save(agendamento);

        return AgendamentoMedicoMapper.mapperToAgendamentoMedicoDto(agendamentoMedicoRepository.findById(agendamentoSalvo.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Agendamento com ID " + agendamentoSalvo.getId() + " não encontrado"))
        );
    }

    @Override
    public AgendamentoMedicoProjection findById(Long id) {
        return agendamentoMedicoRepository.findAgendamentoById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento com ID " + id + " não encontrado"));
    }

    @Override
    public Page<AgendamentoMedicoProjection> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return agendamentoMedicoRepository.findAllAgendamentos(pageable);
    }

    @Override
    public AgendamentoMedicoDto update(Long id, AgendamentoMedicoDto agendamentoDto) {
        AgendamentoMedicoEntity agendamento = agendamentoMedicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento com ID " + id + " não encontrado"));

        MedicoEntity medico = medicoService.findById(agendamentoDto.getMedicoId());
        PacienteEntity paciente = pacienteService.findById(agendamentoDto.getPacienteId());

        validateHorario(agendamentoDto.getDataDaConsulta(), agendamentoDto.getHoraDaConsulta(), id);

        AgendamentoMedicoMapper.mapperToAgendamentoMedicoEntity(agendamento, agendamentoDto, medico, paciente);
        AgendamentoMedicoEntity agendamentoSalvo = agendamentoMedicoRepository.save(agendamento);

        return AgendamentoMedicoMapper.mapperToAgendamentoMedicoDto(agendamentoMedicoRepository.findById(agendamentoSalvo.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Agendamento com ID " + agendamentoSalvo.getId() + " não encontrado"))
        );
    }

    @Override
    public boolean deleteById(Long id) {
        AgendamentoMedicoEntity agendamento = agendamentoMedicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento com ID " + id + " não encontrado"));

        agendamentoMedicoRepository.delete(agendamento);
        return true;
    }
    
    private void validateHorario(LocalDate dataDaConsulta, LocalTime horaDaConsulta, Long agendamentoId) {
        LocalTime inicioHoraMin = horaDaConsulta.minusMinutes(29);
        LocalTime fimHoraMax = horaDaConsulta.plusMinutes(29);

        boolean conflito;
        if (agendamentoId == null) {
            conflito = agendamentoMedicoRepository.findAgendamentosNoHorario(
                dataDaConsulta,
                inicioHoraMin,
                fimHoraMax,
                horaDaConsulta
            ).isEmpty();
        } else {
            conflito = agendamentoMedicoRepository.findAgendamentosByHorarioExceptId(
                dataDaConsulta,
                inicioHoraMin,
                fimHoraMax,
                horaDaConsulta,
                agendamentoId
            ).isEmpty();
        }

        if (!conflito) {
            throw new ConflictException("Já existe um agendamento para esse horário.");
        }
    }

}
