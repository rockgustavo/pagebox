package com.pagebox.pagebox.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pagebox.pagebox.model.entity.Directory;
import com.pagebox.pagebox.model.repository.DirectoryRepository;
import com.pagebox.pagebox.rest.dto.DirectoryDTO;
import com.pagebox.pagebox.service.impl.DirectoryServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class DirectoryServiceTest {
    @MockBean
    private DirectoryRepository directoryRepository;
    @MockBean
    private ModelMapper modelMapper;

    private DirectoryServiceImpl service;

    @BeforeEach
    public void setUp() {
        this.service = new DirectoryServiceImpl(directoryRepository, modelMapper);

    }

    @Test
    @DisplayName("Deve salvar um diretório com sucesso")
    public void salvarDirectoryTest() {
        // Cenário
        DirectoryDTO directoryDTO = DirectoryDTO
                .builder()
                .name("dire1")
                .parentDirectory(null)
                .build();

        Directory directory = Directory
                .builder()
                .id(1L)
                .name("dire1")
                .createdAt(LocalDateTime.now())
                .parentDirectory(null)
                .build();

        // Simula o comportamento do ModelMapper e do repository
        BDDMockito.given(directoryRepository.save(any(Directory.class))).willReturn(directory);
        BDDMockito.given(modelMapper.map(any(Directory.class), eq(DirectoryDTO.class)))
                .willReturn(directoryDTO);

        // Execução
        DirectoryDTO savedDirectoryDTO = service.createDirectory(directoryDTO);

        // Verificação
        assertThat(savedDirectoryDTO).isNotNull();
        assertThat(savedDirectoryDTO.getName()).isEqualTo("dire1");

        // Verifica se o repository e o modelMapper foram chamados corretamente
        BDDMockito.verify(directoryRepository, Mockito.times(1)).save(any(Directory.class));
        BDDMockito.verify(modelMapper, Mockito.times(1)).map(any(Directory.class), eq(DirectoryDTO.class));
    }

    @Test
    @DisplayName("Deve atualizar um diretório com sucesso")
    public void updateDirectoryTest() {
        // Cenário
        Long id = 1L;
        DirectoryDTO directoryDTO = DirectoryDTO.builder()
                .name("dire1 updated")
                .parentDirectory(null) // Sem diretório pai
                .build();

        Directory existingDirectory = Directory.builder()
                .id(id)
                .name("dire1")
                .createdAt(LocalDateTime.now())
                .parentDirectory(null)
                .build();

        Directory updatedDirectory = Directory.builder()
                .id(id)
                .name("dire1 updated")
                .createdAt(LocalDateTime.now())
                .parentDirectory(null)
                .build();

        // Simula o comportamento do repository e do modelMapper
        BDDMockito.given(directoryRepository.findById(id)).willReturn(Optional.of(existingDirectory));
        BDDMockito.given(directoryRepository.save(any(Directory.class))).willReturn(updatedDirectory);
        BDDMockito.given(modelMapper.map(any(Directory.class), eq(DirectoryDTO.class)))
                .willReturn(directoryDTO);

        // Execução
        DirectoryDTO result = service.updateDirectory(id, directoryDTO);

        // Verificação
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("dire1 updated");

        BDDMockito.verify(directoryRepository, Mockito.times(1)).findById(id);
        BDDMockito.verify(directoryRepository, Mockito.times(1)).save(any(Directory.class));
        BDDMockito.verify(modelMapper, Mockito.times(1)).map(any(Directory.class), eq(DirectoryDTO.class));
    }

    @Test
    @DisplayName("Deve deletar um diretório com sucesso")
    public void deleteDirectoryTest() {
        // Cenário
        Long id = 1L;

        // Simula o comportamento do repositório
        BDDMockito.doNothing().when(directoryRepository).deleteById(id);

        // Execução
        service.deleteDirectory(id);

        // Verificação
        BDDMockito.verify(directoryRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Deve listar todos os diretórios com sucesso")
    public void getAllDirectoriesTest() {
        // Cenário
        List<Directory> directories = Arrays.asList(
                Directory.builder().id(1L).name("dire1").build(),
                Directory.builder().id(2L).name("dire2").build());

        List<DirectoryDTO> directoryDTOs = Arrays.asList(
                DirectoryDTO.builder().name("dire1").build(),
                DirectoryDTO.builder().name("dire2").build());

        // Simula o comportamento do repositório e do modelMapper
        BDDMockito.given(directoryRepository.findAllByOrderByParentDirectoryIdAsc()).willReturn(directories);
        BDDMockito.given(modelMapper.map(directories.get(0), DirectoryDTO.class)).willReturn(directoryDTOs.get(0));
        BDDMockito.given(modelMapper.map(directories.get(1), DirectoryDTO.class)).willReturn(directoryDTOs.get(1));

        // Execução
        List<DirectoryDTO> result = service.getAllDirectories();

        // Verificação
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getName()).isEqualTo("dire1");
        assertThat(result.get(1).getName()).isEqualTo("dire2");

        BDDMockito.verify(directoryRepository, Mockito.times(1)).findAllByOrderByParentDirectoryIdAsc();
        BDDMockito.verify(modelMapper, Mockito.times(1)).map(directories.get(0), DirectoryDTO.class);
        BDDMockito.verify(modelMapper, Mockito.times(1)).map(directories.get(1), DirectoryDTO.class);
    }

}
