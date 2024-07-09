package dao;

import entity.Ticket;
import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Value("${user.ticket.update.enabled}")
    private boolean isUpdateEnabled;

    public User findById(int id) {
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, id);
        if (user != null)
            user.getTickets().size();
        session.close();
        return user;
    }

    @Transactional
    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    @Transactional
    public void update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    @Transactional
    public void delete(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(user);
    }

    @Transactional
    public void deleteAllTicketsByUserId(int id) {
        Session session = sessionFactory.getCurrentSession();
        User user = session.get(User.class, id);
        if (user != null) {
            session.createQuery("DELETE FROM Ticket WHERE userId = :userId")
                    .setParameter("userId", id)
                    .executeUpdate();
            session.delete(user);
        }
    }

    @Transactional
    public void updateUserAndTickets(User user, List<Ticket> tickets) {
        if (!isUpdateEnabled) {
            throw new UnsupportedOperationException("Updating User and creating Ticket is disabled");
        }

        Session session = sessionFactory.getCurrentSession();
        session.update(user);
        for (Ticket ticket : tickets) {
            session.update(ticket);
        }
    }

    @Transactional
    public void updateUserAndCreateTicket(User user, Ticket ticket) {
        if (!isUpdateEnabled) {
            throw new UnsupportedOperationException("Updating User and creating Ticket is disabled");
        }

        Session session = sessionFactory.getCurrentSession();
        session.update(user);
        session.save(ticket);
    }
}
