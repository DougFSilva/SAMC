package com.douglas.SAMC.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.douglas.SAMC.service.UploadingService;

@RestController
@RequestMapping(value = "/upload")
public class UploadingController {

	@Autowired
	private UploadingService uploadingService;
	
	@Value("${file.storage}")
	private String fileStorage;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(value = "/{folder}/{name}")
	public ResponseEntity<Void> uploadFile(@RequestParam("image") MultipartFile uploadingFiles,
			@PathVariable String folder, @PathVariable String name) {
		String path;
		if(fileStorage.equals("s3")) {
			path = uploadingService.uploadFileS3(uploadingFiles, "samcs-" + folder, name);
			
		}else {
			path = uploadingService.uploadFile(uploadingFiles, folder, name);
		}
		
		URI uri = ServletUriComponentsBuilder.fromPath(path).buildAndExpand().toUri();
		return ResponseEntity.created(uri).build();

	}
	
}
