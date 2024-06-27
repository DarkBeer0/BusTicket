import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BusTicketValidator {
    public static void main(String[] args) {
        BusTicketService ticketService = new BusTicketService();
        BusTicket ticket1 = ticketService.createTicket("Economy", "DAY", "2024-06-20", 100);
        BusTicket ticket2 = ticketService.createTicket("Economy", "WEEK", "2024-06-21", 200);
        BusTicket ticket3 = ticketService.createTicket("Business", "MONTH", "2024-06-22", 300);
        BusTicket fetchedTicket = ticketService.getTicketById(1);
        System.out.println("Fetched Ticket: " + fetchedTicket.ticketType + " " + fetchedTicket.price);
        List<BusTicket> searchedTickets = ticketService.searchTickets("DAY", 50, 150);
        System.out.println("Searched Tickets: " + searchedTickets.size());
        boolean isRemoved = ticketService.removeTicket(2);
        System.out.println("Ticket 2 removed: " + isRemoved);
    }

    public static List<BusTicket> readTicketsFromFile(String filePath) {
        List<BusTicket> tickets = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            tickets = objectMapper.readValue(Paths.get(filePath).toFile(), new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tickets;
    }
}