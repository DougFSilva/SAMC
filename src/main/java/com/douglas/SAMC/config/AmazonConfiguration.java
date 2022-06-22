package com.douglas.SAMC.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonConfiguration {
	
	@Value("${aws.accessKey}")
	private String ACCESS_KEY;
	
	@Value("${aws.secretKey}")
	private  String SECRET_KEY;
	
	@Value("${aws.region}")
	private  String REGION;
	
	@Bean
	public BasicAWSCredentials basicAWSCredentials() {
		return new BasicAWSCredentials(ACCESS_KEY,SECRET_KEY);
	}
	
	@Bean
	public AmazonS3 amazonS3() {
		return AmazonS3ClientBuilder.standard().withRegion(REGION)
		.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials())).build();
	}
	
}
