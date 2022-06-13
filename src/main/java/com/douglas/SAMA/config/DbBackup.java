package com.douglas.SAMA.config;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DbBackup {

	@Value("${spring.datasource.username}")
	private String dbUserName;

	@Value("${spring.datasource.password}")
	private String dbPassword;

	@Value("${dbName}")
	private String dbName;

	@Value("${sourceFile}")
	private String sourceFile;

	private String date = LocalDate.now().toString();

	@Scheduled(cron = "0 58 23 * * *")
	public void restore() throws IOException, InterruptedException {
		String command = "mysqldump" + " -u" + dbUserName + " -p" + dbPassword + " --databases " + dbName + " -r "
				+ sourceFile + date + "_" + dbName + ".sql";
		Runtime.getRuntime().exec(command);

	}
}
