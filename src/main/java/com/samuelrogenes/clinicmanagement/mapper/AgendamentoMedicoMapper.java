package com.samuelrogenes.clinicmanagement.mapper;

import com.samuelrogenes.clinicmanagement.dtos.AgendamentoMedicoDto;
import com.samuelrogenes.clinicmanagement.entities.AgendamentoMedicoEntity;
import com.samuelrogenes.clinicmanagement.entities.MedicoEntity;
import com.samuelrogenes.clinicmanagement.entities.PacienteEntity;

public class AgendamentoMedicoMapper {

    public static AgendamentoMedicoEntity mapperToAgendamentoMedicoEntity(
            AgendamentoMedicoEntity agendamentoMedicoEntity,
            AgendamentoMedicoDto agendamentoMedicoDto,
            MedicoEntity medico,
            PacienteEntity paciente) {

        agendamentoMedicoEntity.setMedico(medico);
        agendamentoMedicoEntity.setPaciente(paciente);
        agendamentoMedicoEntity.setMotivoDaConsulta(agendamentoMedicoDto.getMotivoDaConsulta());
        agendamentoMedicoEntity.setDataDaConsulta(agendamentoMedicoDto.getDataDaConsulta());
        agendamentoMedicoEntity.setHoraDaConsulta(agendamentoMedicoDto.getHoraDaConsulta());
        agendamentoMedicoEntity.setLocal(agendamentoMedicoDto.getLocal());
        agendamentoMedicoEntity.setObservacoes(agendamentoMedicoDto.getObservacoes());

        return agendamentoMedicoEntity;
    }
}
