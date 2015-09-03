package models;

import java.util.ArrayList;

/**
 * Created by Kristian Kosharov on 3.9.2015 г..
 */
public class Ticket {

    private ArrayList<TicketType> ticketTypes;
//    private int price;
    private int selectNumberOfTickets;
    private int selectedTypePosition;

    public int getSelectedTypePosition() {
        return selectedTypePosition;
    }

    public void setSelectedTypePosition(int selectedTypePosition) {
        this.selectedTypePosition = selectedTypePosition;
    }

    public int getSelectNumberOfTickets() {
        return selectNumberOfTickets;
    }

    public void setSelectNumberOfTickets(int selectNumberOfTickets) {
        this.selectNumberOfTickets = selectNumberOfTickets;
    }

//    public int getPrice() {
//        return price;
//    }
//
//    public void setPrice(int price) {
//        this.price = price;
//    }

    public ArrayList<TicketType> getTicketTypes() {
        return ticketTypes;
    }

    public void setTicketTypes(ArrayList<TicketType> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }
}
