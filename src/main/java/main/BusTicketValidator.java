package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"config", "controller", "entity", "repository", "service", "main"})
public class BusTicketValidator {

    public static void main(String[] args) {
        SpringApplication.run(BusTicketValidator.class, args);
    }
}
