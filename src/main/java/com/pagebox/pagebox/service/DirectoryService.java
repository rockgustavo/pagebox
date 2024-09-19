package com.pagebox.pagebox.service;

import java.util.List;

import com.pagebox.pagebox.rest.dto.DirectoryDTO;

public interface DirectoryService {

    DirectoryDTO createDirectory(DirectoryDTO directoryDTO);

    DirectoryDTO updateDirectory(Long id, DirectoryDTO directoryDTO);

    void deleteDirectory(Long id);

    List<DirectoryDTO> getAllDirectories();

}
