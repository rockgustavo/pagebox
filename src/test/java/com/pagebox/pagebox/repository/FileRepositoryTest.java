package com.pagebox.pagebox.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pagebox.pagebox.model.entity.Directory;
import com.pagebox.pagebox.model.entity.File;
import com.pagebox.pagebox.model.repository.FileRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class FileRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    FileRepository fileRepository;

    Directory directory;
    File file;

    @BeforeEach
    public void setUp() {
        // Criando um diretório para associar ao arquivo
        directory = Directory.builder()
                .name("dir1")
                .build();
        entityManager.persist(directory);

        // Criando um arquivo
        file = File.builder()
                .name("file1")
                .content("https://unsplash.com/imagem")
                .directory(directory)
                .build();
        entityManager.persist(file);
    }

    @Test
    @DisplayName("Deve salvar um arquivo.")
    public void saveFileTest() {
        // Execução
        File savedFile = fileRepository.save(file);

        // Verificação
        assertThat(savedFile.getId()).isNotNull();
        assertThat(savedFile.getName()).isEqualTo("file1");
        assertThat(savedFile.getDirectory().getId()).isEqualTo(directory.getId());
    }

    @Test
    @DisplayName("Deve buscar um arquivo por ID.")
    public void findByIdTest() {
        // Execução
        Optional<File> foundFile = fileRepository.findById(file.getId());

        // Verificação
        assertThat(foundFile.isPresent()).isTrue();
        assertThat(foundFile.get().getId()).isEqualTo(file.getId());
    }

    @Test
    @DisplayName("Deve deletar um arquivo.")
    public void deleteFileTest() {
        // Cenário
        File foundFile = entityManager.find(File.class, file.getId());

        // Execução
        fileRepository.delete(foundFile);

        // Verificação
        File deletedFile = entityManager.find(File.class, file.getId());
        assertThat(deletedFile).isNull();
    }
}
