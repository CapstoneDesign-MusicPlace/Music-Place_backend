package org.musicplace.global.env;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;



@Configuration
public class DotenvConfig {

    @PostConstruct
    public void loadEnvVariables() {
        // 첫 번째 .env 파일 로드
        Dotenv dotenv1 = Dotenv.configure()
                .directory("/home/a01098774665/")
                .filename(".env")
                .load();

        // 두 번째 .env 파일 로드
        Dotenv dotenv2 = Dotenv.configure()
                .directory("/home/")
                .filename(".env")
                .load();

        // 환경 변수에 설정
        dotenv1.entries().forEach(entry -> setSystemProperty(entry.getKey(), entry.getValue()));
        dotenv2.entries().forEach(entry -> setSystemProperty(entry.getKey(), entry.getValue()));
    }

    private void setSystemProperty(String key, String value) {
        if (System.getProperty(key) == null) {
            System.setProperty(key, value);
        }
    }
}
