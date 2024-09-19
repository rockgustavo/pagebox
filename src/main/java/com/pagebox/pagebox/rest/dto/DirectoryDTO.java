package com.pagebox.pagebox.rest.dto;

import java.util.List;

import com.pagebox.pagebox.model.entity.Directory;
import com.pagebox.pagebox.model.entity.File;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectoryDTO {
    private String name;
    private Directory parentDirectory;
    private List<File> files;

}
