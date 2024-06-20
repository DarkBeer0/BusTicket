import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class to manage Bus Tickets with capabilities to create, store, remove, get, and search tickets.
 */
public class BusTicketService {
    private Map<Integer, BusTicket> ticketStorage = new HashMap<>();
    private int currentId = 0;

    /**
     * Creates a BusTicket for the specified date and type.
     * @param ticketClass The class of the ticket.
     * @param ticketType The type of the ticket (DAY, WEEK, MONTH, YEAR).
     * @param startDate The start date of the ticket.
     * @param price The price of the ticket.
     * @return The created BusTicket with a unique ID.
     */
    public BusTicket createTicket(String ticketClass, String ticketType, String startDate, Integer price) {
        BusTicket ticket = new BusTicket(ticketClass, ticketType, startDate, price);
        ticketStorage.put(++currentId, ticket);
        return ticket;
    }

    /**
     * Removes a ticket from the storage by ID.
     * @param ticketId The ID of the ticket to remove.
     * @return true if the ticket was successfully removed, false otherwise.
     */
    public boolean removeTicket(int ticketId) {
        return ticketStorage.remove(ticketId) != null;
    }

    /**
     * Gets a ticket by its ID.
     * @param ticketId The ID of the ticket to retrieve.
     * @return The BusTicket with the specified ID, or null if not found.
     */
    public BusTicket getTicketById(int ticketId) {
        return ticketStorage.get(ticketId);
    }

    /**
     * Searches tickets by type and price range.
     * @param type The type of the tickets to search for (DAY, WEEK, MONTH, YEAR).
     * @param priceA The minimum price of the tickets to search for.
     * @param priceB The maximum price of the tickets to search for.
     * @return A list of BusTickets matching the specified criteria.
     */
    public List<BusTicket> searchTickets(String type, int priceA, int priceB) {
        return ticketStorage.values().stream()
                .filter(ticket -> ticket.ticketType.equals(type) && ticket.price >= priceA && ticket.price <= priceB)
                .collect(Collectors.toList());
    }
}
