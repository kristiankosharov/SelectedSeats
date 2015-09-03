package models;

/**
 * Created by Kristian Kosharov on 3.9.2015 г..
 */
public class TicketType {

    private String ticketType;
    private int ticketPrice;

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
