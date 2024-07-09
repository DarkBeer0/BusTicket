package dao;

import entity.Ticket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TicketDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Ticket findById(int id) {
        Session session = sessionFactory.openSession();
        Ticket ticket = session.get(Ticket.class, id);
        session.close();
        return ticket;
    }

    @Transactional
    public void save(Ticket ticket) {
        Session session = sessionFactory.getCurrentSession();
        session.save(ticket);
    }

    @Transactional
    public void update(Ticket ticket) {
        Session session = sessionFactory.getCurrentSession();
        session.update(ticket);
    }

    @Transactional
    public void delete(Ticket ticket) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(ticket);
    }

    public List<Ticket> findByUserId(int userId) {
        Session session = sessionFactory.openSession();
        List<Ticket> tickets = session.createQuery("FROM Ticket WHERE userId = :userId", Ticket.class)
                .setParameter("userId", userId)
                .list();
        session.close();
        return tickets;
    }
}
