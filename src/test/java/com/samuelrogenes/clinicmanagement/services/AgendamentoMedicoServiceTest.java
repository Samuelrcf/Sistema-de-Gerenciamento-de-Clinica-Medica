package com.samuelrogenes.clinicmanagement.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.samuelrogenes.clinicmanagement.dtos.AgendamentoMedicoDto;
import com.samuelrogenes.clinicmanagement.entities.AgendamentoMedicoEntity;
import com.samuelrogenes.clinicmanagement.entities.MedicoEntity;
import com.samuelrogenes.clinicmanagement.entities.PacienteEntity;
import com.samuelrogenes.clinicmanagement.exceptions.ConflictException;
import com.samuelrogenes.clinicmanagement.projections.AgendamentoMedicoProjection;
import com.samuelrogenes.clinicmanagement.repositories.AgendamentoMedicoRepository;
import com.samuelrogenes.clinicmanagement.services.impl.AgendamentoMedicoService;
import com.samuelrogenes.clinicmanagement.services.impl.MedicoService;
import com.samuelrogenes.clinicmanagement.services.impl.PacienteService;

class AgendamentoMedicoServiceTest {

    @InjectMocks
    private AgendamentoMedicoService agendamentoMedicoService;

    @Mock
    private AgendamentoMedicoRepository agendamentoMedicoRepository;

    @Mock
    private MedicoService medicoService;

    @Mock
    private PacienteService pacienteService;

    private MedicoEntity medico;
    private PacienteEntity paciente;
    private AgendamentoMedicoEntity agendamento;
    private AgendamentoMedicoDto agendamentoDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        medico = new MedicoEntity();
        medico.setId(1L);
        medico.setNomeCompleto("Dr. João");

        paciente = new PacienteEntity();
        paciente.setId(1L);
        paciente.setNomeCompleto("João da Silva");

        agendamento = new AgendamentoMedicoEntity();
        agendamento.setId(1L);
        agendamento.setMedico(medico);
        agendamento.setPaciente(paciente);
        agendamento.setDataDaConsulta(LocalDate.now());
        agendamento.setHoraDaConsulta(LocalTime.now());

        agendamentoDto = new AgendamentoMedicoDto();
        agendamentoDto.setMedicoId(medico.getId());
        agendamentoDto.setPacienteId(paciente.getId());
        agendamentoDto.setDataDaConsulta(LocalDate.now());
        agendamentoDto.setHoraDaConsulta(LocalTime.now());
    }

    @Test
    void testCreate() {
        when(medicoService.findById(any(Long.class))).thenReturn(medico);
        when(pacienteService.findById(any(Long.class))).thenReturn(paciente);
        when(agendamentoMedicoRepository.save(any(AgendamentoMedicoEntity.class))).thenReturn(agendamento);
        when(agendamentoMedicoRepository.findById(any(Long.class))).thenReturn(Optional.of(agendamento));

        AgendamentoMedicoDto createdDto = agendamentoMedicoService.create(agendamentoDto);

        assertThat(createdDto).isNotNull();
        verify(agendamentoMedicoRepository, times(1)).save(any(AgendamentoMedicoEntity.class));
    }

    @Test
    void testFindById() {
        when(agendamentoMedicoRepository.findAgendamentoById(any(Long.class))).thenReturn(Optional.of(mock(AgendamentoMedicoProjection.class)));

        AgendamentoMedicoProjection agendamentoProj = agendamentoMedicoService.findById(1L);

        assertThat(agendamentoProj).isNotNull();
        verify(agendamentoMedicoRepository, times(1)).findAgendamentoById(1L);
    }

    @Test
    void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        when(agendamentoMedicoRepository.findAllAgendamentos(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(mock(AgendamentoMedicoProjection.class))));

        Page<AgendamentoMedicoProjection> page = agendamentoMedicoService.findAll(0, 10);

        assertThat(page).isNotNull();
        assertThat(page.getContent()).isNotEmpty();
        verify(agendamentoMedicoRepository, times(1)).findAllAgendamentos(pageable);
    }

    @Test
    void testUpdate() {
        when(agendamentoMedicoRepository.findById(any(Long.class))).thenReturn(Optional.of(agendamento));
        when(medicoService.findById(any(Long.class))).thenReturn(medico);
        when(pacienteService.findById(any(Long.class))).thenReturn(paciente);
        when(agendamentoMedicoRepository.save(any(AgendamentoMedicoEntity.class))).thenReturn(agendamento);

        AgendamentoMedicoDto updatedDto = agendamentoMedicoService.update(1L, agendamentoDto);

        assertThat(updatedDto).isNotNull();
        verify(agendamentoMedicoRepository, times(1)).save(agendamento);
    }

    @Test
    void testDeleteById() {
        when(agendamentoMedicoRepository.findById(any(Long.class))).thenReturn(Optional.of(agendamento));

        boolean result = agendamentoMedicoService.deleteById(1L);

        assertThat(result).isTrue();
        verify(agendamentoMedicoRepository, times(1)).delete(agendamento);
    }

    @Test
    void testValidateHorarioWithConflict() {
        when(agendamentoMedicoRepository.findAgendamentosNoHorario(any(LocalDate.class), any(LocalTime.class), any(LocalTime.class), any(LocalTime.class)))
                .thenReturn(List.of(agendamento));

        assertThrows(ConflictException.class, () -> agendamentoMedicoService.create(agendamentoDto));
    }
}
