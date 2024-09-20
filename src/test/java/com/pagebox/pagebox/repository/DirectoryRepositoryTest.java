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
import com.pagebox.pagebox.model.repository.DirectoryRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class DirectoryRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    DirectoryRepository directoryRepository;

    Directory directory;

    @BeforeEach
    public void setUp() {
        directory = Directory.builder()
                .name("dir1")
                .build();
        entityManager.persist(directory);
    }

    @Test
    @DisplayName("Deve salvar um diretório.")
    public void saveDirectoryTest() {
        // Execução
        Directory savedDirectory = directoryRepository.save(directory);

        // Verificação
        assertThat(savedDirectory.getId()).isNotNull();
        assertThat(savedDirectory.getName()).isEqualTo("dir1");
    }

    @Test
    @DisplayName("Deve buscar um diretório por ID.")
    public void findByIdTest() {
        // Execução
        Optional<Directory> foundDirectory = directoryRepository.findById(directory.getId());

        // Verificação
        assertThat(foundDirectory.isPresent()).isTrue();
        assertThat(foundDirectory.get().getId()).isEqualTo(directory.getId());
    }

    @Test
    @DisplayName("Deve deletar um diretório.")
    public void deleteDirectoryTest() {
        // Cenário
        Directory foundDirectory = entityManager.find(Directory.class, directory.getId());

        // Execução
        directoryRepository.delete(foundDirectory);

        // Verificação
        Directory deletedDirectory = entityManager.find(Directory.class, directory.getId());
        assertThat(deletedDirectory).isNull();
    }
}
