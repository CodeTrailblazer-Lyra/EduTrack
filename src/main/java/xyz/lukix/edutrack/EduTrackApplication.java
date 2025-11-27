package xyz.lukix.edutrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "xyz.lukix.edutrack.repository")
public class EduTrackApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduTrackApplication.class, args);
    }

}