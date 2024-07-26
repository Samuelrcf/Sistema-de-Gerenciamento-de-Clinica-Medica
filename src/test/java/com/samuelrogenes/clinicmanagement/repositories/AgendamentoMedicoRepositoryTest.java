package com.samuelrogenes.clinicmanagement.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.samuelrogenes.clinicmanagement.dtos.agendamento.AgendamentoMedicoProjection;
import com.samuelrogenes.clinicmanagement.entities.AgendamentoMedicoEntity;
import com.samuelrogenes.clinicmanagement.entities.MedicoEntity;
import com.samuelrogenes.clinicmanagement.entities.PacienteEntity;

@DataJpaTest
public class AgendamentoMedicoRepositoryTest {

    @Autowired
    private AgendamentoMedicoRepository agendamentoMedicoRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Test
    public void testFindAgendamentoById() {
        MedicoEntity medico = new MedicoEntity();
        medicoRepository.save(medico);

        PacienteEntity paciente = new PacienteEntity();
        pacienteRepository.save(paciente);

        AgendamentoMedicoEntity agendamento = new AgendamentoMedicoEntity();
        agendamento.setMedico(medico);
        agendamento.setPaciente(paciente);
        agendamento.setDataDaConsulta(LocalDate.now());
        agendamento.setHoraDaConsulta(LocalTime.now());
        agendamento.setMotivoDaConsulta("Consulta de teste");
        agendamento.setLocal("Local de teste");

        agendamentoMedicoRepository.save(agendamento);

        Optional<AgendamentoMedicoProjection> found = agendamentoMedicoRepository.findAgendamentoById(agendamento.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(agendamento.getId());
    }

    @Test
    public void testFindAllAgendamentos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<AgendamentoMedicoProjection> page = agendamentoMedicoRepository.findAllAgendamentos(pageable);
        assertThat(page).isNotNull();
    }

    @Test
    public void testFindAgendamentosByMedicoAndHorario() {
        MedicoEntity medico = new MedicoEntity();
        medicoRepository.save(medico);

        PacienteEntity paciente = new PacienteEntity();
        pacienteRepository.save(paciente);

        LocalDateTime inicioConsulta = LocalDateTime.now().plusDays(1);
        LocalDateTime fimConsulta = inicioConsulta.plusMinutes(30);

        AgendamentoMedicoEntity agendamento = new AgendamentoMedicoEntity();
        agendamento.setMedico(medico);
        agendamento.setPaciente(paciente);
        agendamento.setDataDaConsulta(inicioConsulta.toLocalDate());
        agendamento.setHoraDaConsulta(inicioConsulta.toLocalTime());
        agendamento.setMotivoDaConsulta("Consulta de teste");
        agendamento.setLocal("Local de teste");

        agendamentoMedicoRepository.save(agendamento);

        Optional<AgendamentoMedicoEntity> found = agendamentoMedicoRepository.findAgendamentosByMedicoAndHorario(medico.getId(), inicioConsulta, fimConsulta);
        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(agendamento.getId());
    }

    @Test
    public void testFindAgendamentosByMedicoAndHorarioExceptId() {
        MedicoEntity medico = new MedicoEntity();
        medicoRepository.save(medico);

        PacienteEntity paciente = new PacienteEntity();
        pacienteRepository.save(paciente);

        LocalDateTime inicioConsulta = LocalDateTime.now().plusDays(1);
        LocalDateTime fimConsulta = inicioConsulta.plusMinutes(30);

        AgendamentoMedicoEntity agendamento = new AgendamentoMedicoEntity();
        agendamento.setMedico(medico);
        agendamento.setPaciente(paciente);
        agendamento.setDataDaConsulta(inicioConsulta.toLocalDate());
        agendamento.setHoraDaConsulta(inicioConsulta.toLocalTime());
        agendamento.setMotivoDaConsulta("Consulta de teste");
        agendamento.setLocal("Local de teste");

        agendamentoMedicoRepository.save(agendamento);

        Optional<AgendamentoMedicoEntity> found = agendamentoMedicoRepository.findAgendamentosByMedicoAndHorarioExceptId(medico.getId(), inicioConsulta, fimConsulta, agendamento.getId());
        assertThat(found).isEmpty();
    }
}
