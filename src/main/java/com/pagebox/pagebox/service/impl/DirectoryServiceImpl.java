package com.pagebox.pagebox.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.pagebox.pagebox.exception.DirectoryNotFoundException;
import com.pagebox.pagebox.model.entity.Directory;
import com.pagebox.pagebox.model.repository.DirectoryRepository;
import com.pagebox.pagebox.rest.dto.DirectoryDTO;
import com.pagebox.pagebox.service.DirectoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DirectoryServiceImpl implements DirectoryService {
    private final DirectoryRepository directoryRepository;
    private final ModelMapper modelMapper;

    public DirectoryDTO createDirectory(DirectoryDTO directoryDTO) {
        Directory directory = new Directory();
        directory.setName(directoryDTO.getName());
        directory.setCreatedAt(LocalDateTime.now());

        // Lógica para associar o diretório pai, se fornecido
        if (directoryDTO.getParentDirectory() != null) {
            Optional<Directory> parentDirectory = directoryRepository
                    .findById(directoryDTO.getParentDirectory().getId());
            if (parentDirectory.isEmpty()) {
                throw new DirectoryNotFoundException("Diretório pai não encontrado");
            }
            directory.setParentDirectory(parentDirectory.get());
        }

        Directory savedDirectory = directoryRepository.save(directory);
        return modelMapper.map(savedDirectory, DirectoryDTO.class);
    }

    public DirectoryDTO updateDirectory(Long id, DirectoryDTO directoryDTO) {
        Directory directory = directoryRepository.findById(id)
                .orElseThrow(() -> new DirectoryNotFoundException("Diretório não encontrado"));

        directory.setName(directoryDTO.getName());

        // Atualiza o diretório pai se informado
        if (directoryDTO.getParentDirectory() != null) {
            Optional<Directory> parentDirectory = directoryRepository
                    .findById(directoryDTO.getParentDirectory().getId());
            if (parentDirectory.isEmpty()) {
                throw new DirectoryNotFoundException("Diretório pai não encontrado");
            }
            directory.setParentDirectory(parentDirectory.get());
        } else {
            // Remove o parent se for nulo
            directory.setParentDirectory(null);
        }

        Directory updatedDirectory = directoryRepository.save(directory);
        return modelMapper.map(updatedDirectory, DirectoryDTO.class);
    }

    // Deleção do diretório
    public void deleteDirectory(Long id) {
        directoryRepository.deleteById(id);
    }

    // Listagem de todos os diretórios ordenados por hierarquia
    public List<DirectoryDTO> getAllDirectories() {
        List<Directory> directories = directoryRepository.findAllByOrderByParentDirectoryIdAsc();
        return directories.stream()
                .map(directory -> modelMapper.map(directory, DirectoryDTO.class))
                .collect(Collectors.toList());
    }

}