package com.samuelrogenes.clinicmanagement.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.samuelrogenes.clinicmanagement.dtos.MedicoDto;
import com.samuelrogenes.clinicmanagement.entities.MedicoEntity;
import com.samuelrogenes.clinicmanagement.entities.PacienteEntity;
import com.samuelrogenes.clinicmanagement.exceptions.ResourceAlreadyExistsException;
import com.samuelrogenes.clinicmanagement.exceptions.ResourceNotFoundException;
import com.samuelrogenes.clinicmanagement.projections.MedicoProjection;
import com.samuelrogenes.clinicmanagement.repositories.MedicoRepository;
import com.samuelrogenes.clinicmanagement.repositories.PacienteRepository;
import com.samuelrogenes.clinicmanagement.services.impl.MedicoService;

@ExtendWith(MockitoExtension.class)
public class MedicoServiceTest {

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private MedicoService medicoService;

    private MedicoDto medicoDto;
    private MedicoEntity medicoEntity;
    private PacienteEntity pacienteEntity;

    @BeforeEach
    public void setUp() {
        medicoDto = new MedicoDto();
        medicoDto.setEmail("medico@example.com");
        medicoDto.setTelefone("123456789");
        medicoDto.setCpf("12345678900");

        medicoEntity = new MedicoEntity();
        medicoEntity.setId(1L);
        medicoEntity.setEmail("medico@example.com");
        medicoEntity.setTelefone("123456789");
        medicoEntity.setCpf("12345678900");

        pacienteEntity = new PacienteEntity();
        pacienteEntity.setEmail("medico@example.com");
        pacienteEntity.setTelefone("123456789");
        pacienteEntity.setCpf("12345678900");
    }

    @Test
    public void testCreateSuccess() {
        when(medicoRepository.findConflictingMedico(anyString(), anyString(), anyString())).thenReturn(new ArrayList<>());
        when(pacienteRepository.findConflictingPaciente(anyString(), anyString(), anyString(), isNull())).thenReturn(new ArrayList<>());
        when(medicoRepository.save(any(MedicoEntity.class))).thenReturn(medicoEntity);
        when(medicoRepository.findById(anyLong())).thenReturn(Optional.of(medicoEntity));

        MedicoDto createdMedico = medicoService.create(medicoDto);

        assertNotNull(createdMedico);
        assertEquals(medicoDto.getEmail(), createdMedico.getEmail());
        verify(medicoRepository, times(1)).save(any(MedicoEntity.class));
    }

    @Test
    public void testCreateConflict() {
        List<MedicoEntity> conflictingMedicos = new ArrayList<>();
        conflictingMedicos.add(medicoEntity);

        when(medicoRepository.findConflictingMedico(anyString(), anyString(), anyString())).thenReturn(conflictingMedicos);
        when(pacienteRepository.findConflictingPaciente(anyString(), anyString(), anyString(), isNull())).thenReturn(new ArrayList<>());

        ResourceAlreadyExistsException thrown = assertThrows(ResourceAlreadyExistsException.class, () -> {
            medicoService.create(medicoDto);
        });

        assertTrue(thrown.getMessage().contains("Email " + medicoDto.getEmail() + " já cadastrado em Médico."));
        verify(medicoRepository, never()).save(any(MedicoEntity.class));
    }

    @Test
    public void testFindByIdSuccess() {
        when(medicoRepository.findById(anyLong())).thenReturn(Optional.of(medicoEntity));

        MedicoEntity foundMedico = medicoService.findById(1L);

        assertNotNull(foundMedico);
        assertEquals(medicoEntity.getEmail(), foundMedico.getEmail());
    }

    @Test
    public void testFindByIdNotFound() {
        when(medicoRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            medicoService.findById(1L);
        });

        assertTrue(thrown.getMessage().contains("Médico com ID 1 não foi encontrado"));
    }

    @Test
    public void testFindMedicoByIdSuccess() {
        when(medicoRepository.findMedicoById(anyLong())).thenReturn(Optional.of(mock(MedicoProjection.class)));

        MedicoProjection foundMedico = medicoService.findMedicoById(1L);

        assertNotNull(foundMedico);
    }

    @Test
    public void testFindMedicoByIdNotFound() {
        when(medicoRepository.findMedicoById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            medicoService.findMedicoById(1L);
        });

        assertTrue(thrown.getMessage().contains("Médico com ID 1 não foi encontrado"));
    }

    @Test
    public void testUpdateSuccess() {
        List<MedicoEntity> conflictingMedicos = new ArrayList<>();
        List<PacienteEntity> conflictingPacientes = new ArrayList<>();

        when(medicoRepository.findById(anyLong())).thenReturn(Optional.of(medicoEntity));
        when(medicoRepository.findConflictingMedico(anyString(), anyString(), anyString())).thenReturn(conflictingMedicos);
        when(pacienteRepository.findConflictingPaciente(anyString(), anyString(), anyString(), isNull())).thenReturn(conflictingPacientes);
        when(medicoRepository.save(any(MedicoEntity.class))).thenReturn(medicoEntity);

        MedicoDto updatedMedico = medicoService.update(1L, medicoDto);

        assertNotNull(updatedMedico);
        assertEquals(medicoDto.getEmail(), updatedMedico.getEmail());
        verify(medicoRepository, times(1)).save(any(MedicoEntity.class));
    }

    @Test
    public void testUpdateConflict() {
        List<MedicoEntity> conflictingMedicos = new ArrayList<>();
        conflictingMedicos.add(medicoEntity);
        List<PacienteEntity> conflictingPacientes = new ArrayList<>();

        when(medicoRepository.findById(anyLong())).thenReturn(Optional.of(medicoEntity));
        when(medicoRepository.findConflictingMedico(anyString(), anyString(), anyString())).thenReturn(conflictingMedicos);
        when(pacienteRepository.findConflictingPaciente(anyString(), anyString(), anyString(), isNull())).thenReturn(conflictingPacientes);

        ResourceAlreadyExistsException thrown = assertThrows(ResourceAlreadyExistsException.class, () -> {
            medicoService.update(1L, medicoDto);
        });

        assertTrue(thrown.getMessage().contains("Email " + medicoDto.getEmail() + " já cadastrado."));
        verify(medicoRepository, never()).save(any(MedicoEntity.class));
    }

    @Test
    public void testDeleteByIdSuccess() {
        when(medicoRepository.findById(anyLong())).thenReturn(Optional.of(medicoEntity));

        boolean result = medicoService.deleteById(1L);

        assertTrue(result);
        verify(medicoRepository, times(1)).delete(any(MedicoEntity.class));
    }

    @Test
    public void testDeleteByIdNotFound() {
        when(medicoRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            medicoService.deleteById(1L);
        });

        assertTrue(thrown.getMessage().contains("Médico com ID 1 não foi encontrado"));
        verify(medicoRepository, never()).delete(any(MedicoEntity.class));
    }
}
