package com.pagebox.pagebox.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @JoinColumn(name = "directory_id")
    private Directory directory; // Relacionamento com diretório

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
