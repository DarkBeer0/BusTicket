package entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "\"ticket\"")
public class Ticket {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "ticket_type")
    private String ticketType;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    public Ticket() {
    }

    public Ticket(int id, int userId, String ticketType, Timestamp creationDate) {
        this.id = id;
        this.userId = userId;
        this.ticketType = ticketType;
        this.creationDate = creationDate;
    }

    public Ticket(int userId, String ticketType, Timestamp creationDate) {
        this.userId = userId;
        this.ticketType = ticketType;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
