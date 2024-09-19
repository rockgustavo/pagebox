package com.pagebox.pagebox.rest.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pagebox.pagebox.rest.dto.DirectoryDTO;
import com.pagebox.pagebox.service.DirectoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/diretorio")
public class DirectoryController {
    private final DirectoryService directoryService;

    // Create: Criação de um novo diretório com ou sem diretório pai
    @PostMapping
    public ResponseEntity<DirectoryDTO> createDirectory(@RequestBody DirectoryDTO directoryDTO) {
        return ResponseEntity.ok(directoryService.createDirectory(directoryDTO));
    }

    // Update: Alterar o nome do diretório ou o diretório pai
    @PutMapping("/{id}")
    public ResponseEntity<DirectoryDTO> updateDirectory(@PathVariable Long id, @RequestBody DirectoryDTO directoryDTO) {
        return ResponseEntity.ok(directoryService.updateDirectory(id, directoryDTO));
    }

    // Delete: Excluir um diretório
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirectory(@PathVariable Long id) {
        directoryService.deleteDirectory(id);
        return ResponseEntity.noContent().build();
    }

    // Read: Obter lista de diretórios ordenados pela hierarquia
    @GetMapping
    public ResponseEntity<List<DirectoryDTO>> getAllDirectories() {
        List<DirectoryDTO> directories = directoryService.getAllDirectories();
        return ResponseEntity.ok(directories);
    }
}