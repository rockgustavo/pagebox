package com.pagebox.pagebox.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "directories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Directory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Directory parentDirectory;

    @OneToMany(mappedBy = "parentDirectory", cascade = CascadeType.ALL)
    private List<Directory> subDirectories;

    @OneToMany(mappedBy = "directory", cascade = CascadeType.ALL)
    private List<File> files;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}