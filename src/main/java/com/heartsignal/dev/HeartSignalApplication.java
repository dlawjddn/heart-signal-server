package com.heartsignal.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableJpaAuditing
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableJpaRepositories(basePackages = "com.heartsignal.dev.repository.rds")
@EnableMongoRepositories(basePackages = "com.heartsignal.dev.repository.nosql")
public class HeartSignalApplication {

	//	@PostConstruct
	//	public void start(){
	//		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	//	}
	public static void main(String[] args) {
		SpringApplication.run(HeartSignalApplication.class, args);
	}

}
