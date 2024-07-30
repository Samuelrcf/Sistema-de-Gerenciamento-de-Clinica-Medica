package com.samuelrogenes.clinicmanagement.services.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.samuelrogenes.clinicmanagement.dtos.MedicoDto;
import com.samuelrogenes.clinicmanagement.entities.MedicoEntity;
import com.samuelrogenes.clinicmanagement.entities.PacienteEntity;
import com.samuelrogenes.clinicmanagement.exceptions.ResourceAlreadyExistsException;
import com.samuelrogenes.clinicmanagement.exceptions.ResourceNotFoundException;
import com.samuelrogenes.clinicmanagement.mapper.MedicoMapper;
import com.samuelrogenes.clinicmanagement.projections.MedicoProjection;
import com.samuelrogenes.clinicmanagement.repositories.MedicoRepository;
import com.samuelrogenes.clinicmanagement.repositories.PacienteRepository;
import com.samuelrogenes.clinicmanagement.services.IMedicoService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MedicoService implements IMedicoService {

	private MedicoRepository medicoRepository;
	private PacienteRepository pacienteRepository;

	@Override
	public MedicoDto create(MedicoDto medicoDto) {
	    List<MedicoEntity> conflictingMedicos = medicoRepository.findConflictingMedico(
	            medicoDto.getEmail(), medicoDto.getTelefone(), medicoDto.getCpf());

	    StringBuilder errorMessage = new StringBuilder("Conflito de dados em médicos:");

	    for (MedicoEntity medico : conflictingMedicos) {
	        if (medico.getEmail().equals(medicoDto.getEmail())) {
	            errorMessage.append(" Email " + medicoDto.getEmail() + " já cadastrado em Médico.");
	        }
	        if (medico.getTelefone().equals(medicoDto.getTelefone())) {
	            errorMessage.append(" Telefone " + medicoDto.getTelefone() + " já cadastrado em Médico.");
	        }
	        if (medico.getCpf().equals(medicoDto.getCpf())) {
	            errorMessage.append(" CPF " + medicoDto.getCpf() + " já cadastrado em Médico.");
	        }
	    }

	    List<PacienteEntity> conflictingPacientes = pacienteRepository.findConflictingPaciente(
	            medicoDto.getEmail(), medicoDto.getTelefone(), medicoDto.getCpf(), null);

	    for (PacienteEntity paciente : conflictingPacientes) {
	        if (paciente.getEmail().equals(medicoDto.getEmail())) {
	            errorMessage.append(" Email " + medicoDto.getEmail() + " já cadastrado em Paciente.");
	        }
	        if (paciente.getTelefone().equals(medicoDto.getTelefone())) {
	            errorMessage.append(" Telefone " + medicoDto.getTelefone() + " já cadastrado em Paciente.");
	        }
	        if (paciente.getCpf().equals(medicoDto.getCpf())) {
	            errorMessage.append(" CPF " + medicoDto.getCpf() + " já cadastrado em Paciente.");
	        }
	    }

	    if (errorMessage.length() > "Conflito de dados em médicos:".length()) {
	        throw new ResourceAlreadyExistsException(errorMessage.toString());
	    }

	    MedicoEntity medicoMapeado = MedicoMapper.mapperToMedicoEntity(new MedicoEntity(), medicoDto);
	    MedicoEntity medicoSalvo = medicoRepository.save(medicoMapeado);

	    return MedicoMapper.mapperToMedicoDto(medicoRepository.findById(medicoSalvo.getId())
	                    .orElseThrow(() -> new ResourceNotFoundException("Médico com ID " + medicoSalvo.getId() + " não encontrado"))
	    );
	}

	@Override
	public MedicoEntity findById(Long id) {
		MedicoEntity medico = medicoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Médico com ID " + id + " não foi encontrado"));
		return medico;
	}

	@Override
	public MedicoProjection findMedicoById(Long id) {
		MedicoProjection medico = medicoRepository.findMedicoById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Médico com ID " + id + " não foi encontrado"));
		return medico;
	}

	@Override
	public Page<MedicoProjection> findAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return medicoRepository.findAllMedicos(pageable);
	}
	
	@Override
	public MedicoDto update(Long id, MedicoDto medicoDto) {
	    MedicoEntity medicoExistente = medicoRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Médico com ID " + id + " não encontrado"));

	    List<MedicoEntity> conflictingMedicos = medicoRepository.findConflictingMedico(
	            medicoDto.getEmail(), medicoDto.getTelefone(), medicoDto.getCpf());
	    List<PacienteEntity> conflictingPacientes = pacienteRepository.findConflictingPaciente(
	            medicoDto.getEmail(), medicoDto.getTelefone(), medicoDto.getCpf(), null); // RG é nulo para médicos

	    StringBuilder errorMessage = new StringBuilder("Conflito de dados:");

	    for (MedicoEntity medico : conflictingMedicos) {
	        if (!medico.getId().equals(id)) {
	            if (medico.getEmail().equals(medicoDto.getEmail())) {
	                errorMessage.append(" Email " + medicoDto.getEmail() + " já cadastrado.");
	            }
	            if (medico.getTelefone().equals(medicoDto.getTelefone())) {
	                errorMessage.append(" Telefone " + medicoDto.getTelefone() + " já cadastrado.");
	            }
	            if (medico.getCpf().equals(medicoDto.getCpf())) {
	                errorMessage.append(" CPF " + medicoDto.getCpf() + " já cadastrado.");
	            }
	        }
	    }

	    for (PacienteEntity paciente : conflictingPacientes) {
	        if (paciente.getEmail().equals(medicoDto.getEmail())) {
	            errorMessage.append(" Email " + medicoDto.getEmail() + " já cadastrado em Paciente.");
	        }
	        if (paciente.getTelefone().equals(medicoDto.getTelefone())) {
	            errorMessage.append(" Telefone " + medicoDto.getTelefone() + " já cadastrado em Paciente.");
	        }
	        if (paciente.getCpf().equals(medicoDto.getCpf())) {
	            errorMessage.append(" CPF " + medicoDto.getCpf() + " já cadastrado em Paciente.");
	        }
	    }

	    if (errorMessage.length() > "Conflito de dados:".length()) {
	        throw new ResourceAlreadyExistsException(errorMessage.toString());
	    }

	    MedicoMapper.mapperToMedicoEntity(medicoExistente, medicoDto);
	    MedicoEntity medicoSalvo = medicoRepository.save(medicoExistente);

	    return MedicoMapper.mapperToMedicoDto(
	            medicoRepository.findById(medicoSalvo.getId())
	                    .orElseThrow(() -> new ResourceNotFoundException("Médico com ID " + medicoSalvo.getId() + " não encontrado"))
	    );
	}

	@Transactional
	@Override
	public boolean deleteById(Long id) {
		MedicoEntity medico = medicoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Médico com ID " + id + " não foi encontrado"));
		medicoRepository.delete(medico);
		return true;
	}

}
