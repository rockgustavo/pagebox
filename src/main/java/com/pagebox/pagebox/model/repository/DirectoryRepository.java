package com.pagebox.pagebox.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pagebox.pagebox.model.entity.Directory;

public interface DirectoryRepository extends JpaRepository<Directory, Long> {

}
