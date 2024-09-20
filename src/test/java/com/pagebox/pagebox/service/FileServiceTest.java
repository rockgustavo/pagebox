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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pagebox.pagebox.model.entity.Directory;
import com.pagebox.pagebox.model.entity.File;
import com.pagebox.pagebox.model.repository.DirectoryRepository;
import com.pagebox.pagebox.model.repository.FileRepository;
import com.pagebox.pagebox.rest.dto.FileDTO;
import com.pagebox.pagebox.service.impl.FileServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class FileServiceTest {
    @MockBean
    private FileRepository fileRepository;
    @MockBean
    private DirectoryRepository directoryRepository;
    @MockBean
    private ModelMapper modelMapper;

    private FileServiceImpl service;

    @BeforeEach
    public void setUp() {
        this.service = new FileServiceImpl(fileRepository, directoryRepository, modelMapper);

    }

    @Test
    @DisplayName("Deve criar um arquivo com sucesso")
    public void createFileTest() {
        // Cenário
        FileDTO fileDTO = FileDTO.builder()
                .name("file1")
                .content("content")
                .directory(Directory.builder().id(1L).build())
                .build();

        Directory directory = Directory.builder()
                .id(1L)
                .name("dir1")
                .build();

        File file = File.builder()
                .id(1L)
                .name("file1")
                .content("content")
                .directory(directory)
                .createdAt(LocalDateTime.now())
                .build();

        // Simula o comportamento do repositório e do modelMapper
        BDDMockito.given(directoryRepository.findById(1L)).willReturn(Optional.of(directory));
        BDDMockito.given(fileRepository.save(any(File.class))).willReturn(file);
        BDDMockito.given(modelMapper.map(any(File.class), eq(FileDTO.class))).willReturn(fileDTO);

        // Execução
        FileDTO result = service.createFile(fileDTO);

        // Verificação
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("file1");
        assertThat(result.getContent()).isEqualTo("content");

        BDDMockito.verify(directoryRepository, Mockito.times(1)).findById(1L);
        BDDMockito.verify(fileRepository, Mockito.times(1)).save(any(File.class));
        BDDMockito.verify(modelMapper, Mockito.times(1)).map(any(File.class), eq(FileDTO.class));
    }

    @Test
    @DisplayName("Deve atualizar um arquivo com sucesso")
    public void updateFileTest() {
        // Cenário
        Long id = 1L;
        FileDTO fileDTO = FileDTO.builder()
                .name("file1 updated")
                .content("updated content")
                .directory(Directory.builder().id(1L).build())
                .build();

        File existingFile = File.builder()
                .id(id)
                .name("file1")
                .content("content")
                .directory(Directory.builder().id(1L).build())
                .build();

        File updatedFile = File.builder()
                .id(id)
                .name("file1 updated")
                .content("updated content")
                .directory(Directory.builder().id(1L).build())
                .build();

        Directory directory = Directory.builder()
                .id(1L)
                .name("dir1")
                .build();

        // Simula o comportamento do repositório e do modelMapper
        BDDMockito.given(fileRepository.findById(id)).willReturn(Optional.of(existingFile));
        BDDMockito.given(directoryRepository.findById(1L)).willReturn(Optional.of(directory));
        BDDMockito.given(fileRepository.save(any(File.class))).willReturn(updatedFile);
        BDDMockito.given(modelMapper.map(any(File.class), eq(FileDTO.class))).willReturn(fileDTO);

        // Execução
        FileDTO result = service.updateFile(id, fileDTO);

        // Verificação
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("file1 updated");
        assertThat(result.getContent()).isEqualTo("updated content");

        BDDMockito.verify(fileRepository, Mockito.times(1)).findById(id);
        BDDMockito.verify(directoryRepository, Mockito.times(1)).findById(1L);
        BDDMockito.verify(fileRepository, Mockito.times(1)).save(any(File.class));
        BDDMockito.verify(modelMapper, Mockito.times(1)).map(any(File.class), eq(FileDTO.class));
    }

    @Test
    @DisplayName("Deve deletar um arquivo com sucesso")
    public void deleteFileTest() {
        // Cenário
        Long id = 1L;

        // Simula o comportamento do repositório
        BDDMockito.doNothing().when(fileRepository).deleteById(id);

        // Execução
        service.deleteFile(id);

        // Verificação
        BDDMockito.verify(fileRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Deve buscar um arquivo por ID com sucesso")
    public void getFileByIdTest() {
        // Cenário
        Long id = 1L;
        File file = File.builder()
                .id(id)
                .name("file1")
                .content("content")
                .build();

        FileDTO fileDTO = FileDTO.builder()
                .name("file1")
                .content("content")
                .build();

        // Simula o comportamento do repositório e do modelMapper
        BDDMockito.given(fileRepository.findById(id)).willReturn(Optional.of(file));
        BDDMockito.given(modelMapper.map(file, FileDTO.class)).willReturn(fileDTO);

        // Execução
        Optional<FileDTO> result = service.getFileById(id);

        // Verificação
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("file1");

        BDDMockito.verify(fileRepository, Mockito.times(1)).findById(id);
        BDDMockito.verify(modelMapper, Mockito.times(1)).map(file, FileDTO.class);
    }

    @Test
    @DisplayName("Deve buscar um arquivo por nome com sucesso")
    public void getFileByNameTest() {
        // Cenário
        String name = "file1";
        File file = File.builder()
                .id(1L)
                .name(name)
                .content("content")
                .build();

        FileDTO fileDTO = FileDTO.builder()
                .name(name)
                .content("content")
                .build();

        // Simula o comportamento do repositório e do modelMapper
        BDDMockito.given(fileRepository.findByName(name)).willReturn(Optional.of(file));
        BDDMockito.given(modelMapper.map(file, FileDTO.class)).willReturn(fileDTO);

        // Execução
        Optional<FileDTO> result = service.getFileByName(name);

        // Verificação
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo(name);

        BDDMockito.verify(fileRepository, Mockito.times(1)).findByName(name);
        BDDMockito.verify(modelMapper, Mockito.times(1)).map(file, FileDTO.class);
    }

    @Test
    @DisplayName("Deve listar arquivos com paginação")
    public void getAllFilesTest() {
        // Cenário
        Pageable pageable = PageRequest.of(0, 10);
        List<File> files = Arrays.asList(
                File.builder().id(1L).name("file1").content("content1").build(),
                File.builder().id(2L).name("file2").content("content2").build());

        Page<File> filePage = new PageImpl<>(files, pageable, files.size());

        List<FileDTO> fileDTOs = Arrays.asList(
                FileDTO.builder().name("file1").content("content1").build(),
                FileDTO.builder().name("file2").content("content2").build());

        // Simula o comportamento do repositório e do modelMapper
        BDDMockito.given(fileRepository.findAll(pageable)).willReturn(filePage);
        BDDMockito.given(modelMapper.map(files.get(0), FileDTO.class)).willReturn(fileDTOs.get(0));
        BDDMockito.given(modelMapper.map(files.get(1), FileDTO.class)).willReturn(fileDTOs.get(1));

        // Execução
        Page<FileDTO> result = service.getAllFiles(pageable);

        // Verificação
        assertThat(result).isNotNull();
        assertThat(result.getContent().size()).isEqualTo(2);
        assertThat(result.getContent().get(0).getName()).isEqualTo("file1");
        assertThat(result.getContent().get(1).getName()).isEqualTo("file2");

        BDDMockito.verify(fileRepository, Mockito.times(1)).findAll(pageable);
        BDDMockito.verify(modelMapper, Mockito.times(1)).map(files.get(0), FileDTO.class);
        BDDMockito.verify(modelMapper, Mockito.times(1)).map(files.get(1), FileDTO.class);
    }

}
