package com.samuelrogenes.clinicmanagement.repositories;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.samuelrogenes.clinicmanagement.entities.MedicoEntity;
import com.samuelrogenes.clinicmanagement.projections.MedicoProjection;

@ExtendWith(MockitoExtension.class)
public class MedicoRepositoryTest {

    @Mock
    private MedicoRepository medicoRepository;

    @InjectMocks
    private MedicoRepositoryTest medicoRepositoryTest;

    private MedicoEntity medico;
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
        
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void testFindById() {
        when(medicoRepository.findById(1L)).thenReturn(Optional.of(medico));

        Optional<MedicoEntity> result = medicoRepository.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getNomeCompleto()).isEqualTo("Dr. João Silva");
    }

    @Test
    void testFindConflictingMedico() {
        List<MedicoEntity> medicos = List.of(medico);
        when(medicoRepository.findConflictingMedico("joao.silva@example.com", "999999999", "12345678900"))
            .thenReturn(medicos);

        List<MedicoEntity> result = medicoRepository.findConflictingMedico("joao.silva@example.com", "999999999", "12345678900");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNomeCompleto()).isEqualTo("Dr. João Silva");
    }

    @Test
    void testFindMedicoByIdProjection() {
        MedicoProjection projection = new MedicoProjection() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getNomeCompleto() {
                return "Dr. João Silva";
            }

            @Override
            public String getCpf() {
                return "12345678900";
            }

            @Override
            public String getConselhoMedico() {
                return "CRM";
            }

            @Override
            public String getNumeroDoConselho() {
                return "123456";
            }

            @Override
            public String getCBO() {
                return "1234";
            }
        };

        when(medicoRepository.findMedicoById(1L)).thenReturn(Optional.of(projection));

        Optional<MedicoProjection> result = medicoRepository.findMedicoById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getNomeCompleto()).isEqualTo("Dr. João Silva");
    }

    @Test
    void testFindAllMedicos() {
        MedicoProjection projection = new MedicoProjection() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getNomeCompleto() {
                return "Dr. João Silva";
            }

            @Override
            public String getCpf() {
                return "12345678900";
            }

            @Override
            public String getConselhoMedico() {
                return "CRM";
            }

            @Override
            public String getNumeroDoConselho() {
                return "123456";
            }

            @Override
            public String getCBO() {
                return "1234";
            }
        };

        Page<MedicoProjection> pagedResult = new PageImpl<>(List.of(projection), pageable, 1);
        when(medicoRepository.findAllMedicos(pageable)).thenReturn(pagedResult);

        Page<MedicoProjection> result = medicoRepository.findAllMedicos(pageable);
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getNomeCompleto()).isEqualTo("Dr. João Silva");
    }
}
