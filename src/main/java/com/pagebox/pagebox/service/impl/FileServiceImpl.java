package com.pagebox.pagebox.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pagebox.pagebox.exception.DirectoryNotFoundException;
import com.pagebox.pagebox.exception.FileNotFoundException;
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

        @Override
        public FileDTO createFile(FileDTO fileDTO) {
                if (fileDTO.getDirectory() == null || fileDTO.getDirectory().getId() == null) {
                        throw new IllegalArgumentException("Um diretório deve ser fornecido");
                }

                Directory directory = directoryRepository.findById(fileDTO.getDirectory().getId())
                                .orElseThrow(() -> new DirectoryNotFoundException("Diretório não encontrado"));

                File file = new File();
                file.setName(fileDTO.getName());
                file.setContent(fileDTO.getContent());
                file.setDirectory(directory);
                file.setCreatedAt(LocalDateTime.now());

                File savedFile = fileRepository.save(file);
                return modelMapper.map(savedFile, FileDTO.class);
        }

        @Override
        public FileDTO updateFile(Long id, FileDTO fileDTO) {
                File file = fileRepository.findById(id)
                                .orElseThrow(() -> new FileNotFoundException("Arquivo não encontrado"));

                if (fileDTO.getDirectory() != null && fileDTO.getDirectory().getId() != null) {
                        Directory directory = directoryRepository.findById(fileDTO.getDirectory().getId())
                                        .orElseThrow(() -> new DirectoryNotFoundException("Diretório não encontrado"));
                        file.setDirectory(directory);
                } else {
                        file.setDirectory(null); // Remove o diretório se não for fornecido
                }

                file.setName(fileDTO.getName());
                file.setContent(fileDTO.getContent());

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