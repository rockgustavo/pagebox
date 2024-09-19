package com.pagebox.pagebox.rest.dto;

import com.pagebox.pagebox.model.entity.Directory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {
    private String name;
    private String content;
    private Directory directory;
}
