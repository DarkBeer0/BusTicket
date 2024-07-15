package main;

import entity.Ticket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import service.TicketService;

import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = {
        "config",
        "repository",
        "service",
        "controller"
})
@EnableJpaRepositories(basePackages = "repository")
@EntityScan(basePackages = "entity")
public class TicketServiceApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(TicketServiceApplication.class, args);
        TicketService ticketService = context.getBean(TicketService.class);
        List<Ticket> tickets = ticketService.getTickets();
        if (context.containsBean("thisIsMyFirstConditionalBean")) {
            System.out.println(context.getBean("thisIsMyFirstConditionalBean"));
        } else {
            System.out.println("Conditional bean is not loaded");
        }
    }
}
