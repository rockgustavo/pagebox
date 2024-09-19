package com.pagebox.pagebox.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pagebox.pagebox.model.entity.Directory;
import com.pagebox.pagebox.model.entity.File;
import com.pagebox.pagebox.model.repository.DirectoryRepository;
import com.pagebox.pagebox.model.repository.FileRepository;
import com.pagebox.pagebox.rest.dto.FileDTO;
import com.pagebox.pagebox.service.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final DirectoryRepository directoryRepository;
    private final ModelMapper modelMapper;

    // Criação de um arquivo
    @Override
    public FileDTO createFile(FileDTO fileDTO) {
        Directory directory = fileDTO.getDirectory() != null
                ? directoryRepository.findById(fileDTO.getDirectory().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Diretório não encontrado"))
                : null;

        File file = new File();
        file.setName(fileDTO.getName());
        file.setContent(fileDTO.getContent());
        file.setDirectory(directory);
        file.setCreatedAt(LocalDateTime.now());

        File savedFile = fileRepository.save(file);
        return modelMapper.map(savedFile, FileDTO.class);
    }

    // Atualização de um arquivo
    @Override
    public FileDTO updateFile(Long id, FileDTO fileDTO) {
        File file = fileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Arquivo não encontrado"));

        Directory directory = fileDTO.getDirectory() != null
                ? directoryRepository.findById(fileDTO.getDirectory().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Diretório não encontrado"))
                : null;

        file.setName(fileDTO.getName());
        file.setContent(fileDTO.getContent());
        file.setDirectory(directory);

        File updatedFile = fileRepository.save(file);
        return modelMapper.map(updatedFile, FileDTO.class);
    }

    // Exclusão de um arquivo
    @Override
    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }

    // Busca de um arquivo por ID
    @Override
    public Optional<FileDTO> getFileById(Long id) {
        return fileRepository.findById(id)
                .map(file -> modelMapper.map(file, FileDTO.class));
    }

    // Busca de um arquivo por nome
    @Override
    public Optional<FileDTO> getFileByName(String name) {
        return fileRepository.findByName(name)
                .map(file -> modelMapper.map(file, FileDTO.class));
    }

    // Listagem paginada de arquivos
    @Override
    public Page<FileDTO> getAllFiles(Pageable pageable) {
        return fileRepository.findAll(pageable)
                .map(file -> modelMapper.map(file, FileDTO.class));
    }
}