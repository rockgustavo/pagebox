package com.pagebox.pagebox.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pagebox.pagebox.rest.dto.FileDTO;

public interface FileService {
    FileDTO createFile(FileDTO fileDTO);

    FileDTO updateFile(Long id, FileDTO fileDTO);

    void deleteFile(Long id);

    Optional<FileDTO> getFileById(Long id);

    Optional<FileDTO> getFileByName(String name);

    Page<FileDTO> getAllFiles(Pageable pageable);

}
