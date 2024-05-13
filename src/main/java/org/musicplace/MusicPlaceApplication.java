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

}
