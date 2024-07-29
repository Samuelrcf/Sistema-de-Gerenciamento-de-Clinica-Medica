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
    
    public static AgendamentoMedicoDto mapperToAgendamentoMedicoDto(AgendamentoMedicoEntity agendamentoMedicoEntity) {
        AgendamentoMedicoDto agendamentoMedicoDto = new AgendamentoMedicoDto();
        agendamentoMedicoDto.setMedicoId(agendamentoMedicoEntity.getMedico().getId());
        agendamentoMedicoDto.setPacienteId(agendamentoMedicoEntity.getPaciente().getId());
        agendamentoMedicoDto.setMotivoDaConsulta(agendamentoMedicoEntity.getMotivoDaConsulta());
        agendamentoMedicoDto.setDataDaConsulta(agendamentoMedicoEntity.getDataDaConsulta());
        agendamentoMedicoDto.setHoraDaConsulta(agendamentoMedicoEntity.getHoraDaConsulta());
        agendamentoMedicoDto.setLocal(agendamentoMedicoEntity.getLocal());
        agendamentoMedicoDto.setObservacoes(agendamentoMedicoEntity.getObservacoes());
        return agendamentoMedicoDto;
    }
}
