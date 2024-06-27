import java.sql.*;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class TicketServiceDAO {
    private Connection connection;

    public TicketServiceDAO() {
        try {
            Properties properties = new Properties();
            InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties");
            properties.load(input);

            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name) throws SQLException {
        String sql = "INSERT INTO public.user (name) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    public void saveTicket(int userId, String ticketType) throws SQLException {
        if (!isValidTicketType(ticketType)) {
            throw new IllegalArgumentException("Invalid ticket type: " + ticketType);
        }
        String sql = "INSERT INTO public.ticket (user_id, ticket_type) VALUES (?, ?::ticket_type)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, ticketType);
            pstmt.executeUpdate();
        }
    }

    private boolean isValidTicketType(String ticketType) {
        return ticketType.equals("DAY") || ticketType.equals("WEEK") || ticketType.equals("MONTH") || ticketType.equals("YEAR");
    }

    public void fetchTicketById(int id) throws SQLException {
        String sql = "SELECT * FROM public.ticket WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("User ID: " + rs.getInt("user_id"));
                System.out.println("Ticket Type: " + rs.getString("ticket_type"));
                System.out.println("Creation Date: " + rs.getTimestamp("creation_date"));
            }
        }
    }

    public void fetchTicketsByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM public.ticket WHERE user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("User ID: " + rs.getInt("user_id"));
                System.out.println("Ticket Type: " + rs.getString("ticket_type"));
                System.out.println("Creation Date: " + rs.getTimestamp("creation_date"));
            }
        }
    }

    public void fetchUserById(int id) throws SQLException {
        String sql = "SELECT * FROM public.user WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Creation Date: " + rs.getTimestamp("creation_date"));
            }
        }
    }

    public void updateTicketType(int id, String ticketType) throws SQLException {
        String sql = "UPDATE public.ticket SET ticket_type = ?::ticket_type WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, ticketType);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteUserById(int id) throws SQLException {
        String sqlDeleteTickets = "DELETE FROM public.ticket WHERE user_id = ?";
        String sqlDeleteUser = "DELETE FROM public.user WHERE id = ?";
        try (PreparedStatement pstmtTickets = connection.prepareStatement(sqlDeleteTickets);
             PreparedStatement pstmtUser = connection.prepareStatement(sqlDeleteUser)) {
            pstmtTickets.setInt(1, id);
            pstmtTickets.executeUpdate();
            pstmtUser.setInt(1, id);
            pstmtUser.executeUpdate();
        }
    }

    public void updateUserAndTicketWithSavepoint(int userId, String newUserName, int ticketId, String newTicketType) throws SQLException {
        Savepoint savepoint = null;
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint("Savepoint1");

            String sqlUpdateUser = "UPDATE public.user SET name = ? WHERE id = ?";
            try (PreparedStatement pstmtUser = connection.prepareStatement(sqlUpdateUser)) {
                pstmtUser.setString(1, newUserName);
                pstmtUser.setInt(2, userId);
                pstmtUser.executeUpdate();
            }

            String sqlUpdateTicket = "UPDATE public.ticket SET ticket_type = ?::ticket_type WHERE id = ?";
            try (PreparedStatement pstmtTicket = connection.prepareStatement(sqlUpdateTicket)) {
                pstmtTicket.setString(1, newTicketType);
                pstmtTicket.setInt(2, ticketId);
                pstmtTicket.executeUpdate();
            }

            connection.commit();
        } catch (SQLException e) {
            if (savepoint != null) {
                connection.rollback(savepoint);
                connection.commit();
            } else {
                connection.rollback();
            }
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
    }
}