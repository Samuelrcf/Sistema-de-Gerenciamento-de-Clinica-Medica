package com.samuelrogenes.clinicmanagement.services.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.samuelrogenes.clinicmanagement.dtos.medico.MedicoDto;
import com.samuelrogenes.clinicmanagement.dtos.medico.MedicoProjection;
import com.samuelrogenes.clinicmanagement.entities.MedicoEntity;
import com.samuelrogenes.clinicmanagement.exceptions.ResourceAlreadyExistsException;
import com.samuelrogenes.clinicmanagement.exceptions.ResourceNotFoundException;
import com.samuelrogenes.clinicmanagement.mapper.MedicoMapper;
import com.samuelrogenes.clinicmanagement.repositories.MedicoRepository;
import com.samuelrogenes.clinicmanagement.services.IMedicoService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MedicoService implements IMedicoService {

	private MedicoRepository medicoRepository;

	@Override
	public MedicoProjection create(MedicoDto medicoDto) {

		List<MedicoEntity> conflictingMedicos = medicoRepository.findConflictingMedico(medicoDto.getEmail(),
				medicoDto.getTelefone(), medicoDto.getCpf());

		StringBuilder errorMessage = new StringBuilder("Conflito de dados:");

		for (MedicoEntity medico : conflictingMedicos) {
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

		if (errorMessage.length() > "Conflito de dados:".length()) {
			throw new ResourceAlreadyExistsException(errorMessage.toString());
		}

		MedicoEntity medicoMapeado = MedicoMapper.mapperToMedicoEntity(new MedicoEntity(), medicoDto);
		MedicoEntity medicoSalvo = medicoRepository.save(medicoMapeado);

		return medicoRepository.findMedicoById(medicoSalvo.getId()).orElseThrow(
				() -> new ResourceNotFoundException("Médico com ID " + medicoSalvo.getId() + " não encontrado"));
	}

	@Override
	public MedicoProjection findById(Long id) {
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
	public MedicoProjection update(Long id, MedicoDto medicoDto) {
	    // Recupera o médico existente
	    MedicoEntity medicoExistente = medicoRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("Médico com ID " + id + " não foi encontrado"));

	    // Encontra médicos que podem ter o mesmo e-mail, telefone ou CPF
	    List<MedicoEntity> conflictingMedicos = medicoRepository.findConflictingMedico(medicoDto.getEmail(), medicoDto.getTelefone(), medicoDto.getCpf());

	    // Cria uma StringBuilder para construir a mensagem de erro
	    StringBuilder errorMessage = new StringBuilder("Conflito de dados:");

	    // Verifica os médicos encontrados
	    for (MedicoEntity medico : conflictingMedicos) {
	        // Verifica se o médico encontrado não é o mesmo que está sendo atualizado
	        if (!medico.getId().equals(id)) {
	            // Verifica conflitos para e-mail, telefone e CPF
	            boolean emailConflict = medico.getEmail().equals(medicoDto.getEmail()) && !medico.getEmail().equals(medicoExistente.getEmail());
	            boolean telefoneConflict = medico.getTelefone().equals(medicoDto.getTelefone()) && !medico.getTelefone().equals(medicoExistente.getTelefone());
	            boolean cpfConflict = medico.getCpf().equals(medicoDto.getCpf()) && !medico.getCpf().equals(medicoExistente.getCpf());

	            // Adiciona mensagens de erro conforme necessário
	            if (emailConflict) {
	                errorMessage.append(" Email " + medicoDto.getEmail() + " já cadastrado.");
	            }
	            if (telefoneConflict) {
	                errorMessage.append(" Telefone " + medicoDto.getTelefone() + " já cadastrado.");
	            }
	            if (cpfConflict) {
	                errorMessage.append(" CPF " + medicoDto.getCpf() + " já cadastrado.");
	            }
	        }
	    }

	    // Lança uma exceção se houver conflitos
	    if (errorMessage.length() > "Conflito de dados:".length()) {
	        throw new ResourceAlreadyExistsException(errorMessage.toString());
	    }

	    // Atualiza e salva o médico
	    MedicoMapper.mapperToMedicoEntity(medicoExistente, medicoDto);
	    MedicoEntity medicoSalvo = medicoRepository.save(medicoExistente);

	    // Retorna o médico salvo
	    return medicoRepository.findMedicoById(medicoSalvo.getId())
	            .orElseThrow(() -> new ResourceNotFoundException("Médico com ID " + medicoSalvo.getId() + " não encontrado"));
	}


	@Override
	public boolean deleteById(Long id) {
		MedicoEntity medico = medicoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Médico com ID " + id + " não foi encontrado"));
		medicoRepository.delete(medico);
		return true;
	}

}
