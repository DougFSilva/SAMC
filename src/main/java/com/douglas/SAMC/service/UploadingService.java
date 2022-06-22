package com.douglas.SAMC.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.douglas.SAMC.service.Exception.UploadFailedException;

@Service
public class UploadingService {

	@Value("${uploadPhotoLocation}")
	private String uploadPhotoLocation;

	@Autowired
	private AmazonS3 amazonS3;


	public String uploadFile(MultipartFile file, String folder, String name) {
		try {
			InputStream inputStream = file.getInputStream();
			Files.copy(inputStream, Paths.get(this.uploadPhotoLocation + "/" + folder + "/" + name),
					StandardCopyOption.REPLACE_EXISTING);
			return folder + "/" + name;
		} catch (Exception e) {
			throw new UploadFailedException("Falha no upload local do arquivo!");
		}

	}

	public String uploadFileS3(MultipartFile file, String bucket, String name) {
		try {
			amazonS3.putObject(new PutObjectRequest(bucket, name, file.getInputStream(), null)
					.withCannedAcl(CannedAccessControlList.PublicRead));
			return "https://s3." + amazonS3.getRegionName() + ".amazonaws.com/" + bucket + "/" + name;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UploadFailedException("Falha no upload do arquivo na AWS S3!");
		}

	}
}
