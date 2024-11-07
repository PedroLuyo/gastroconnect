package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
public class MsDishesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsDishesApplication.class, args);
	}

}
