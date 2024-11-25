package org.musicplace.global.env;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;



/*
@Configuration
public class DotenvConfig {

    @PostConstruct
    public void loadEnvVariables() {

        Dotenv dotenv = Dotenv.configure()
                .directory("/data/")
                .filename(".env")
                .load();

        // 환경 변수에 설정
        dotenv.entries().forEach(entry -> setSystemProperty(entry.getKey(), entry.getValue()));
    }

    private void setSystemProperty(String key, String value) {
        if (System.getProperty(key) == null) {
            System.setProperty(key, value);
        }
    }
}
*/
