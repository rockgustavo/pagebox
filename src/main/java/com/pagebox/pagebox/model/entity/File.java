package com.pagebox.pagebox.model.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotEmpty(message = "Nome do arquivo é obrigatório")
    private String name;

    @Column(nullable = false)
    @NotEmpty(message = "O conteúdo não pode ser vazio")
    private String content;

    @ManyToOne
    @JoinColumn(name = "directory_id", nullable = false)
    @JsonBackReference // Evita a serialização infinita do diretório relacionado
    private Directory directory;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
