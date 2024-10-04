package pl.kurs.finaltesttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "pl.kurs.finaltesttest")
@EnableJpaRepositories(basePackages = "pl.kurs.finaltesttest.repository")
@EntityScan(basePackages = "pl.kurs.finaltesttest.model")
public class FinalTestTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalTestTestApplication.class, args);
    }

}
