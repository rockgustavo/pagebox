package com.pagebox.pagebox.rest.dto;

import com.pagebox.pagebox.model.entity.Directory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {

    @NotBlank(message = "Nome do arquivo não pode ser em branco")
    private String name;
    @NotBlank(message = "Conteúdo não pode ser em branco")
    private String content;
    @NotNull(message = "Este arquivo precisa pertencer à um diretório")
    private Directory directory;
}
