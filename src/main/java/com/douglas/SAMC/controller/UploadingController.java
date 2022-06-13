package com.douglas.SAMC.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/upload")
public class UploadingController {

	@Value("${uploadPhotoLocation}")
	private String uploadPhotoLocation;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/{folder}/{name}")
	public ResponseEntity<Void> uploadFile(@RequestParam("image") MultipartFile[] uploadingFiles,
			@PathVariable String folder, @PathVariable String name) throws IllegalStateException, IOException {
		InputStream inputStream = uploadingFiles[0].getInputStream();
		Files.copy(inputStream, Paths.get(this.uploadPhotoLocation + "/" + folder + "/" + name),
				StandardCopyOption.REPLACE_EXISTING);
		return ResponseEntity.ok().build();

	}
}
