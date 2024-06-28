import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusTicketValidator {
    public static void main(String[] args) {
        TicketServiceDAO dao = new TicketServiceDAO();
        try {
            dao.saveUser("Bum Tolik");
            dao.fetchUserById(2);
            dao.saveTicket(2, "WEEK");
            dao.fetchTicketById(2);
            dao.fetchTicketsByUserId(2);
            dao.deleteUserById(1);
            dao.updateTicketType(4, "WEEK");
            dao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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