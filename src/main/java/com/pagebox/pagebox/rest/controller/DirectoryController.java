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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/diretorio")
public class DirectoryController {
    private final DirectoryService directoryService;

    @Operation(summary = "Criação de um novo diretório (com ou sem diretório pai)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Diretório criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping
    public ResponseEntity<DirectoryDTO> createDirectory(@Valid @RequestBody DirectoryDTO directoryDTO) {
        return ResponseEntity.ok(directoryService.createDirectory(directoryDTO));
    }

    @Operation(summary = "Alterar o nome do diretório ou o diretório pai")
    @PutMapping("/{id}")
    public ResponseEntity<DirectoryDTO> updateDirectory(@Valid @PathVariable Long id,
            @RequestBody DirectoryDTO directoryDTO) {
        return ResponseEntity.ok(directoryService.updateDirectory(id, directoryDTO));
    }

    @Operation(summary = "Excluir um diretório")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirectory(@PathVariable Long id) {
        directoryService.deleteDirectory(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obter lista de diretórios ordenados pela hierarquia")
    @GetMapping
    public ResponseEntity<List<DirectoryDTO>> getAllDirectories() {
        List<DirectoryDTO> directories = directoryService.getAllDirectories();
        return ResponseEntity.ok(directories);
    }
}