package org.musicplace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;



@SpringBootApplication
public class MusicPlaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicPlaceApplication.class, args);

	}

	@Component
	public class SwaggerInformation implements ApplicationRunner {
		@Value("${server.port}")
		private String port;
		Logger logger = LoggerFactory.getLogger("Main");
		@Override
		public void run(ApplicationArguments args) {
			logger.info("Swagger : http://localhost:" + port  + "/swagger-ui/index.html");
		}
	}


}
