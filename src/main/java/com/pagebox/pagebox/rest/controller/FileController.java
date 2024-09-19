package com.pagebox.pagebox.rest.controller;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pagebox.pagebox.rest.dto.FileDTO;
import com.pagebox.pagebox.service.FileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/arquivo")
public class FileController {
    private final FileService fileService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void save(@Valid @RequestBody FileDTO dto) {

    }

}
