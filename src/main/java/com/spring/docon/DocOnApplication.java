package com.spring.docon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class DocOnApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocOnApplication.class, args);
	}

}
