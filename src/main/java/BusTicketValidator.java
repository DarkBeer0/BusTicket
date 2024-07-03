import entity.Ticket;
import entity.User;

import java.sql.Timestamp;
import java.util.List;

public class BusTicketValidator {
    public static void main(String[] args) {
        TicketDAO ticketDAO = new TicketDAO();
        UserDAO userDAO = new UserDAO();
        userDAO.deleteAllTicketsByUserId(2);
    }
}