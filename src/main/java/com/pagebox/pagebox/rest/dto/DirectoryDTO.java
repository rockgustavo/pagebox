package com.pagebox.pagebox.rest.dto;

import java.util.List;

import com.pagebox.pagebox.model.entity.Directory;
import com.pagebox.pagebox.model.entity.File;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectoryDTO {
    private Long id;
    @NotBlank(message = "Nome do diretório não pode ser em branco")
    private String name;
    private Directory parentDirectory;
    private List<File> files;

}
