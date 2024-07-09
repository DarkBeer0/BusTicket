package main;

import config.AppConfig;
import dao.TicketDAO;
import dao.UserDAO;
import entity.Ticket;
import entity.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.TicketService;

import java.sql.Timestamp;
import java.util.List;

public class BusTicketValidator {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        TicketDAO ticketDAO = context.getBean(TicketDAO.class);
        UserDAO userDAO = context.getBean(UserDAO.class);
        TicketService ticketService = context.getBean(TicketService.class);

        User user = userDAO.findById(151);
        Ticket ticket = new Ticket(6,151, "VIP", new Timestamp(System.currentTimeMillis()));
        userDAO.updateUserAndCreateTicket(user, ticket);

        System.out.println("User ID: " + user.getId());
        System.out.println("Number of tickets: " + user.getTickets().size());

        List<Ticket> ticketsFromFile = ticketService.getTickets();
        System.out.println("Tickets from file: " + ticketsFromFile);

        context.close();
    }
}
