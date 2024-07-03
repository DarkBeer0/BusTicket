import entity.Ticket;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAO {
    public User findById(int id) {
        return SessionFactoryProvider.getSessionFactory().openSession().get(User.class, id);
    }

    public void save(User user) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    public void update(User user) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        transaction.commit();
        session.close();
    }

    public void delete(User user) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        session.close();
    }

    public void deleteAllTicketsByUserId(int id) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        if (user != null) {
            session.createQuery("DELETE FROM Ticket WHERE userId = :userId")
                    .setParameter("userId", id)
                    .executeUpdate();
            session.delete(user);
        }
        transaction.commit();
        session.close();
    }

    public void updateUserAndTickets(User user, List<Ticket> tickets) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(user);
        for (Ticket ticket : tickets) {
            session.update(ticket);
        }
        transaction.commit();
        session.close();
    }
}
