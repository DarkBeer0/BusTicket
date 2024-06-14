import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BusTicketValidator {
    public static void main(String[] args) {
        List<BusTicket> tickets = readTicketsFromFile("D:\\Java\\BusTicket\\tickets.txt");
        Validator validator = new Validator();
        int totalTickets = tickets.size();
        int validTickets = 0;

        for (BusTicket ticket : tickets) {
            List<String> violations = validator.validate(ticket);
            if (violations.isEmpty()) {
                validTickets++;
            } else {
                System.out.println("Ticket violated rules: " + violations);
            }
        }

        System.out.println("Total = " + totalTickets);
        System.out.println("Valid = " + validTickets);
        System.out.println("Most popular violation = " + validator.getMostPopularViolation());
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