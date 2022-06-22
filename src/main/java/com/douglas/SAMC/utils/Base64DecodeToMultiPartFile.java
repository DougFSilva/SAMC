package com.douglas.SAMC.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

public class Base64DecodeToMultiPartFile implements MultipartFile{
	
	private final byte[] fileContent;
	
	public Base64DecodeToMultiPartFile(byte[] fileContent) {
		this.fileContent = fileContent;
	}
	
	public Base64DecodeToMultiPartFile(String base64) {
		String base64Content = base64.split(",")[1];
		fileContent = Base64.getDecoder().decode(base64Content);
		
	}
	

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOriginalFilename() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long getSize() {
		return fileContent.length;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return fileContent;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(fileContent);
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
	}

}
