import entity.Ticket;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TicketDAO {
    public Ticket findById(int id) {
        return SessionFactoryProvider.getSessionFactory().openSession().get(Ticket.class, id);
    }

    public void save(Ticket ticket) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(ticket);
        transaction.commit();
        session.close();
    }

    public void update(Ticket ticket) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(ticket);
        transaction.commit();
        session.close();
    }

    public void delete(Ticket ticket) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(ticket);
        transaction.commit();
        session.close();
    }

    public List<Ticket> findByUserId(int userId) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        List<Ticket> tickets = session.createQuery("FROM Ticket WHERE userId = :userId", Ticket.class)
                .setParameter("userId", userId)
                .list();
        session.close();
        return tickets;
    }
}
