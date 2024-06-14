public class BusTicket {
    public String ticketClass;
    public String ticketType;
    public String startDate;
    public Integer price;

    public BusTicket() {
    }

    public BusTicket(String ticketClass, String ticketType, String startDate, Integer price) {
        this.ticketClass = ticketClass;
        this.ticketType = ticketType;
        this.startDate = startDate;
        this.price = price;
    }
}