package com.samuelrogenes.clinicmanagement.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.samuelrogenes.clinicmanagement.dtos.agendamento.AgendamentoMedicoDto;
import com.samuelrogenes.clinicmanagement.dtos.agendamento.AgendamentoMedicoProjection;
import com.samuelrogenes.clinicmanagement.entities.AgendamentoMedicoEntity;
import com.samuelrogenes.clinicmanagement.entities.MedicoEntity;
import com.samuelrogenes.clinicmanagement.entities.PacienteEntity;
import com.samuelrogenes.clinicmanagement.exceptions.ConflictException;
import com.samuelrogenes.clinicmanagement.exceptions.ResourceNotFoundException;
import com.samuelrogenes.clinicmanagement.mapper.AgendamentoMedicoMapper;
import com.samuelrogenes.clinicmanagement.repositories.AgendamentoMedicoRepository;
import com.samuelrogenes.clinicmanagement.repositories.MedicoRepository;
import com.samuelrogenes.clinicmanagement.repositories.PacienteRepository;
import com.samuelrogenes.clinicmanagement.services.IAgendamentoMedicoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AgendamentoMedicoService implements IAgendamentoMedicoService {

    private AgendamentoMedicoRepository agendamentoMedicoRepository;
    private MedicoRepository medicoRepository;
    private PacienteRepository pacienteRepository;

    @Override
    public AgendamentoMedicoProjection create(AgendamentoMedicoDto agendamentoDto) {
        MedicoEntity medico = medicoRepository.findById(agendamentoDto.getMedicoId())
                .orElseThrow(() -> new ResourceNotFoundException("Médico com ID " + agendamentoDto.getMedicoId() + " não encontrado"));

        PacienteEntity paciente = pacienteRepository.findById(agendamentoDto.getPacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente com ID " + agendamentoDto.getPacienteId() + " não encontrado"));

        validateHorario(agendamentoDto.getMedicoId(), agendamentoDto.getDataDaConsulta(), agendamentoDto.getHoraDaConsulta());

        AgendamentoMedicoEntity agendamento = AgendamentoMedicoMapper.mapperToAgendamentoMedicoEntity(new AgendamentoMedicoEntity(), agendamentoDto, medico, paciente);
        AgendamentoMedicoEntity agendamentoSalvo = agendamentoMedicoRepository.save(agendamento);

        return agendamentoMedicoRepository.findAgendamentoById(agendamentoSalvo.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento com ID " + agendamentoSalvo.getId() + " não encontrado"));
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
    public AgendamentoMedicoProjection update(Long id, AgendamentoMedicoDto agendamentoDto) {
        AgendamentoMedicoEntity agendamento = agendamentoMedicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento com ID " + id + " não encontrado"));

        MedicoEntity medico = medicoRepository.findById(agendamentoDto.getMedicoId())
                .orElseThrow(() -> new ResourceNotFoundException("Médico com ID " + agendamentoDto.getMedicoId() + " não encontrado"));

        PacienteEntity paciente = pacienteRepository.findById(agendamentoDto.getPacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente com ID " + agendamentoDto.getPacienteId() + " não encontrado"));

        validateHorario(agendamentoDto.getMedicoId(), agendamentoDto.getDataDaConsulta(), agendamentoDto.getHoraDaConsulta(), id);

        AgendamentoMedicoMapper.mapperToAgendamentoMedicoEntity(agendamento, agendamentoDto, medico, paciente);
        AgendamentoMedicoEntity agendamentoSalvo = agendamentoMedicoRepository.save(agendamento);

        return agendamentoMedicoRepository.findAgendamentoById(agendamentoSalvo.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento com ID " + agendamentoSalvo.getId() + " não encontrado"));
    }

    @Override
    public boolean deleteById(Long id) {
        AgendamentoMedicoEntity agendamento = agendamentoMedicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento com ID " + id + " não encontrado"));

        agendamentoMedicoRepository.delete(agendamento);
        return true;
    }

    private void validateHorario(Long medicoId, LocalDate dataDaConsulta, LocalTime horaDaConsulta) {
        LocalDateTime inicioConsulta = LocalDateTime.of(dataDaConsulta, horaDaConsulta);
        LocalDateTime fimConsulta = inicioConsulta.plusMinutes(30);

        boolean conflito = agendamentoMedicoRepository.findAgendamentosByMedicoAndHorario(medicoId, inicioConsulta, fimConsulta).isPresent();

        if (conflito) {
            throw new ConflictException("Já existe um agendamento para o médico nesse horário.");
        }
    }

    private void validateHorario(Long medicoId, LocalDate dataDaConsulta, LocalTime horaDaConsulta, Long agendamentoId) {
        LocalDateTime inicioConsulta = LocalDateTime.of(dataDaConsulta, horaDaConsulta);
        LocalDateTime fimConsulta = inicioConsulta.plusMinutes(30);

        boolean conflito = agendamentoMedicoRepository.findAgendamentosByMedicoAndHorarioExceptId(medicoId, inicioConsulta, fimConsulta, agendamentoId).isPresent();

        if (conflito) {
            throw new ConflictException("Já existe um agendamento para o médico nesse horário.");
        }
    }
}
