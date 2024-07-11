import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusTicketValidator {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TicketServiceDAO dao = context.getBean(TicketServiceDAO.class);

        try {
            dao.fetchUserById(16);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}