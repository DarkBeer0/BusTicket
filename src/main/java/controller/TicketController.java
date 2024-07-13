package controller;

import entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.TicketRepository;

@RestController
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/ticket")
    public Ticket getTicket(@RequestParam int id) {
        return ticketRepository.findById(id).orElse(null);
    }
}
