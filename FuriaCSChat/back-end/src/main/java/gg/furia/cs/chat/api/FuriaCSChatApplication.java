package gg.furia.cs.chat.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"gg.furia.cs.chat"})
@EnableJpaRepositories(basePackages = "gg.furia.cs.chat.core.repository")
@EntityScan(basePackages = "gg.furia.cs.chat.core.entity")
public class FuriaCSChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(FuriaCSChatApplication.class, args);
    }
}