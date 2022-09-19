package com.example.pywo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PyWoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PyWoApplication.class, args);
	/*	ExecutorService executor = Executors.newFixedThreadPool(5);
		for (int i = 1; i <= 10; i++) {
			executor.submit(new PythonScriptRunner("Thread_" + i));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		executor.shutdown();*/
	}

}
