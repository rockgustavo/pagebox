package com.pagebox.pagebox.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pagebox.pagebox.model.entity.File;

public interface FileRepository extends JpaRepository<File, Long> {

}
