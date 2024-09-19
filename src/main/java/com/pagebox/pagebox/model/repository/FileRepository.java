package com.pagebox.pagebox.model.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.pagebox.pagebox.model.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findByName(String name);

    @NonNull
    Page<File> findAll(@NonNull Pageable pageable); // Para paginação
}
