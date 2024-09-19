package com.pagebox.pagebox.rest.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pagebox.pagebox.rest.dto.FileDTO;
import com.pagebox.pagebox.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/arquivo")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    // Create: Criação de um arquivo associado a um diretório
    @PostMapping
    public ResponseEntity<FileDTO> createFile(@RequestBody FileDTO fileDTO) {
        return ResponseEntity.ok(fileService.createFile(fileDTO));
    }

    // Update: Atualizar o arquivo, incluindo mudança de diretório, nome e conteúdo
    @PutMapping("/{id}")
    public ResponseEntity<FileDTO> updateFile(@PathVariable Long id, @RequestBody FileDTO fileDTO) {
        return ResponseEntity.ok(fileService.updateFile(id, fileDTO));
    }

    // Delete: Excluir um arquivo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

    // Read 1: Buscar um arquivo por ID
    @GetMapping("/{id}")
    public ResponseEntity<FileDTO> getFileById(@PathVariable Long id) {
        return fileService.getFileById(id)
                .map(fileDTO -> ResponseEntity.ok(fileDTO))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/nome")
    public ResponseEntity<FileDTO> getFileByName(@RequestBody FileDTO fileDTO) {
        return fileService.getFileByName(fileDTO.getName())
                .map(file -> ResponseEntity.ok(file))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Read 2: Buscar lista de arquivos com paginação
    @GetMapping
    public ResponseEntity<List<FileDTO>> getAllFiles(Pageable pageable) {
        Page<FileDTO> filesPage = fileService.getAllFiles(pageable);
        return ResponseEntity.ok(filesPage.getContent());
    }
}