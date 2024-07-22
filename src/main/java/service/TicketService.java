package service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Ticket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class TicketService {

    @Value("classpath:tickets.json")
    private Resource ticketsResource;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Ticket> tickets;

    @PostConstruct
    public void init() throws IOException {
        try {
            tickets = loadTicketsFromFile();
            System.out.println("Tickets loaded successfully " + tickets.size());
        } catch (IOException e) {
            System.out.println("Failed to load tickets from file");
        }
    }

    public List<Ticket> loadTicketsFromFile() throws IOException {
        return objectMapper.readValue(ticketsResource.getInputStream(), new TypeReference<List<Ticket>>() {});
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
}
