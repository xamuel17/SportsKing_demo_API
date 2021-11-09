package com.sport.service.implementation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sport.exception.MyFileNotFoundException;
import com.sport.models.User;
import com.sport.service.FilesStorageService;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

	
  private final Path root = Paths.get("uploads");
  private final Path fileStorageLocation = null;

  @Override
  public void init() {
    try {
      Files.createDirectory(root);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }

  @Override
  public User save(MultipartFile file, User user) {
    try {
    	Random random = new Random();
    	String id = String.format("%04d", random.nextInt(10000));
    	
    	String filename = user.getUsername()+ "-"+ id +"-"+file.getOriginalFilename(); 
    	
    	   String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                   .path("/uploads/")
                   .path(filename)
                   .toUriString();
    	   
    	user.setPhoto(fileDownloadUri);
      Files.copy(file.getInputStream(), this.root.resolve(filename));
      return user;
    } catch (Exception e) {
      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }
  }

  @Override
  public Resource load(String filename) {
    try {
      Path file = root.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read the file!");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(root.toFile());
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
    } catch (IOException e) {
      throw new RuntimeException("Could not load the files!");
    }
  }
  
  
  
  
  
  
  
  
  
  
  
  public Resource loadFileAsResource(String fileName) {
      try {
          Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
          Resource resource = new UrlResource(filePath.toUri());
          if(resource.exists()) {
              return resource;
          } else {
              throw new MyFileNotFoundException("File not found " + fileName);
          }
      } catch (MalformedURLException ex) {
          throw new MyFileNotFoundException("File not found " + fileName, ex);
      }
  }
  
  
}
