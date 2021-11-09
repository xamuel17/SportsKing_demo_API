package com.sport.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.sport.models.User;

public interface FilesStorageService {
  public void init();

  public User save(MultipartFile file, User user);

  public Resource load(String filename);

  public void deleteAll();

  public Stream<Path> loadAll();

public Resource loadFileAsResource(String fileName);
}
