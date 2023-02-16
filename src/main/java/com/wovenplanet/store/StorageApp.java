package com.wovenplanet.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class StorageApp {

	public static void main(String[] args) {
		SpringApplication.run(StorageApp.class, args);
	}

}
