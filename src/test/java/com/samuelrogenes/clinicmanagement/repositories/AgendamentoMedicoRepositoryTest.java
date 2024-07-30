package com.samuelrogenes.clinicmanagement.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.samuelrogenes.clinicmanagement.entities.AgendamentoMedicoEntity;
import com.samuelrogenes.clinicmanagement.entities.MedicoEntity;
import com.samuelrogenes.clinicmanagement.entities.PacienteEntity;
import com.samuelrogenes.clinicmanagement.projections.AgendamentoMedicoProjection;

@DataJpaTest
public class AgendamentoMedicoRepositoryTest {

    @Autowired
    private AgendamentoMedicoRepository agendamentoMedicoRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    private MedicoEntity medico;
    private PacienteEntity paciente;
    private AgendamentoMedicoEntity agendamento;

    @BeforeEach
    void setUp() {
        medico = new MedicoEntity();
        medico.setNomeCompleto("Dr. João");
        medicoRepository.save(medico);

        paciente = new PacienteEntity();
        paciente.setNomeCompleto("João da Silva");
        pacienteRepository.save(paciente);

        agendamento = new AgendamentoMedicoEntity();
        agendamento.setMedico(medico);
        agendamento.setPaciente(paciente);
        agendamento.setDataDaConsulta(LocalDate.now());
        agendamento.setHoraDaConsulta(LocalTime.now());
        agendamentoMedicoRepository.save(agendamento);
    }

    @Test
    void testFindAgendamentoById() {
        Optional<AgendamentoMedicoProjection> agendamentoProj = agendamentoMedicoRepository.findAgendamentoById(agendamento.getId());
        assertThat(agendamentoProj).isPresent();
        assertThat(agendamentoProj.get().getId()).isEqualTo(agendamento.getId());
        assertThat(agendamentoProj.get().getMedicoNomeCompleto()).isEqualTo(medico.getNomeCompleto());
        assertThat(agendamentoProj.get().getPacienteNomeCompleto()).isEqualTo(paciente.getNomeCompleto());
    }

    @Test
    void testFindAllAgendamentos() {
        Pageable pageable = PageRequest.of(0, 10);
        var agendamentos = agendamentoMedicoRepository.findAllAgendamentos(pageable);
        assertThat(agendamentos.getTotalElements()).isGreaterThan(0);
    }

    @Test
    void testFindAgendamentosNoHorario() {
        LocalDate dataConsulta = LocalDate.now();
        LocalTime inicioHoraMin = agendamento.getHoraDaConsulta().minusMinutes(20);
        LocalTime fimHoraMax = agendamento.getHoraDaConsulta().plusMinutes(20);
        LocalTime inicioHoraMax = agendamento.getHoraDaConsulta().plusMinutes(10);
        List<AgendamentoMedicoEntity> agendamentos = agendamentoMedicoRepository.findAgendamentosNoHorario(
            dataConsulta, inicioHoraMin, fimHoraMax, inicioHoraMax, medico.getId(), paciente.getId()
        );
        assertThat(agendamentos).isNotEmpty();
    }

    @Test
    void testFindAgendamentosByHorarioExceptId() {
        LocalDate dataConsulta = LocalDate.now();
        LocalTime inicioHoraMin = agendamento.getHoraDaConsulta().minusMinutes(20);
        LocalTime fimHoraMax = agendamento.getHoraDaConsulta().plusMinutes(20);
        LocalTime inicioHoraMax = agendamento.getHoraDaConsulta().plusMinutes(10);
        List<AgendamentoMedicoEntity> agendamentos = agendamentoMedicoRepository.findAgendamentosByHorarioExceptId(
            dataConsulta, inicioHoraMin, fimHoraMax, inicioHoraMax, medico.getId(), paciente.getId(), agendamento.getId()
        );
        assertThat(agendamentos).isEmpty();
    }
}
