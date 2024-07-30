package com.samuelrogenes.clinicmanagement.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.samuelrogenes.clinicmanagement.dtos.MedicoDto;
import com.samuelrogenes.clinicmanagement.entities.MedicoEntity;
import com.samuelrogenes.clinicmanagement.exceptions.ResourceAlreadyExistsException;
import com.samuelrogenes.clinicmanagement.mapper.MedicoMapper;
import com.samuelrogenes.clinicmanagement.projections.MedicoProjection;
import com.samuelrogenes.clinicmanagement.repositories.MedicoRepository;
import com.samuelrogenes.clinicmanagement.services.impl.MedicoService;

@ExtendWith(MockitoExtension.class)
public class MedicoServiceTest {

    @Mock
    private MedicoRepository medicoRepository;

    @InjectMocks
    private MedicoService medicoService;

    private MedicoEntity medico;
    private MedicoDto medicoDto;
    private MedicoProjection medicoProjection;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        medico = new MedicoEntity();
        medico.setId(1L);
        medico.setNomeCompleto("Dr. João Silva");
        medico.setCpf("12345678900");
        medico.setEmail("joao.silva@example.com");
        medico.setTelefone("999999999");
        medico.setConselhoMedico("CRM");
        medico.setNumeroDoConselho("123456");
        medico.setCBO("1234");

        medicoDto = new MedicoDto();
        medicoDto.setNomeCompleto("Dr. João Silva");
        medicoDto.setCpf("12345678900");
        medicoDto.setEmail("joao.silva@example.com");
        medicoDto.setTelefone("999999999");
        medicoDto.setConselhoMedico("CRM");
        medicoDto.setNumeroDoConselho("123456");
        medicoDto.setCbo("1234");

        medicoProjection = new MedicoProjection() {
            @Override
            public Long getId() { return 1L; }
            @Override
            public String getNomeCompleto() { return "Dr. João Silva"; }
            @Override
            public String getCpf() { return "12345678900"; }
            @Override
            public String getConselhoMedico() { return "CRM"; }
            @Override
            public String getNumeroDoConselho() { return "123456"; }
            @Override
            public String getCBO() { return "1234"; }
        };

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void testCreate() {
        when(medicoRepository.findConflictingMedico(any(), any(), any())).thenReturn(List.of());
        when(MedicoMapper.mapperToMedicoEntity(any(MedicoEntity.class), any(MedicoDto.class))).thenReturn(medico);
        when(medicoRepository.save(any(MedicoEntity.class))).thenReturn(medico);
        when(MedicoMapper.mapperToMedicoDto(any(MedicoEntity.class))).thenReturn(medicoDto);

        MedicoDto result = medicoService.create(medicoDto);

        assertThat(result).isNotNull();
        assertThat(result.getNomeCompleto()).isEqualTo("Dr. João Silva");
    }

    @Test
    void testFindById() {
        when(medicoRepository.findById(anyLong())).thenReturn(Optional.of(medico));

        MedicoEntity result = medicoService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getNomeCompleto()).isEqualTo("Dr. João Silva");
    }

    @Test
    void testFindMedicoByIdProjection() {
        when(medicoRepository.findMedicoById(anyLong())).thenReturn(Optional.of(medicoProjection));

        MedicoProjection result = medicoService.findMedicoById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getNomeCompleto()).isEqualTo("Dr. João Silva");
    }

    @Test
    void testFindAll() {
        Page<MedicoProjection> pagedResult = new PageImpl<>(List.of(medicoProjection), pageable, 1);
        when(medicoRepository.findAllMedicos(any(Pageable.class))).thenReturn(pagedResult);

        Page<MedicoProjection> result = medicoService.findAll(0, 10);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getNomeCompleto()).isEqualTo("Dr. João Silva");
    }

    @Test
    void testUpdate() {
        when(medicoRepository.findById(anyLong())).thenReturn(Optional.of(medico));
        when(medicoRepository.findConflictingMedico(any(), any(), any())).thenReturn(List.of());
        when(MedicoMapper.mapperToMedicoEntity(any(MedicoEntity.class), any(MedicoDto.class))).thenReturn(medico);
        when(medicoRepository.save(any(MedicoEntity.class))).thenReturn(medico);
        when(MedicoMapper.mapperToMedicoDto(any(MedicoEntity.class))).thenReturn(medicoDto);

        MedicoDto result = medicoService.update(1L, medicoDto);

        assertThat(result).isNotNull();
        assertThat(result.getNomeCompleto()).isEqualTo("Dr. João Silva");
    }

    @Test
    void testDeleteById() {
        when(medicoRepository.findById(anyLong())).thenReturn(Optional.of(medico));

        boolean result = medicoService.deleteById(1L);

        verify(medicoRepository).delete(medico);
        assertThat(result).isTrue();
    }

    @Test
    void testCreateWithConflictingData() {
        when(medicoRepository.findConflictingMedico(any(), any(), any())).thenReturn(List.of(medico));

        Throwable thrown = catchThrowable(() -> medicoService.create(medicoDto));

        assertThat(thrown).isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("Conflito de dados:");
    }

    @Test
    void testUpdateWithConflictingData() {
        when(medicoRepository.findById(anyLong())).thenReturn(Optional.of(medico));
        when(medicoRepository.findConflictingMedico(any(), any(), any())).thenReturn(List.of(medico));

        Throwable thrown = catchThrowable(() -> medicoService.update(1L, medicoDto));

        assertThat(thrown).isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("Conflito de dados:");
    }
}
