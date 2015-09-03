package adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.frt.selectseats.R;

import java.util.ArrayList;

import interfaces.DeleteTicket;
import models.Ticket;
import models.TicketType;

/**
 * Created by Kristian Kosharov on 3.9.2015 г..
 */
public class TicketTypesAdapter extends BaseAdapter {

    private static final String TAG = TicketTypesAdapter.class.getName();
    private Context context;
    private ArrayList<Ticket> reservations;
    private ArrayList<Ticket> tickets;
    //    private int number = 1;
    private static final int MAX_OF_SELECTED_SEAT = 10;
    //    private int countOfSelectedTickets = 1;
    //    private int positionOfSpinner = 0;
    private DeleteTicket mListener;
    private ArrayList<TicketType> ticketTypes;

    public TicketTypesAdapter(Context context, ArrayList<Ticket> firstTicket) {
        this.context = context;
        this.tickets = firstTicket;
        mListener = (DeleteTicket) context;
    }

    @Override
    public int getCount() {
        return tickets.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder = null;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.list_view_ticket_type_item, parent, false);
            holder = new ViewHolder();
            holder.ticketTypesSpinner = (Spinner) view.findViewById(R.id.ticket_type);
            holder.priceForOneTicket = (EditText) view.findViewById(R.id.price_for_one);
            holder.totalPrice = (EditText) view.findViewById(R.id.total_price);
            holder.numberOfTickets = (TextView) view.findViewById(R.id.number);
            holder.increaseNumberOfTickets = (ImageButton) view.findViewById(R.id.button_up);
            holder.decreaseNumberOfTickets = (ImageButton) view.findViewById(R.id.button_down);
            holder.deleteButton = (ImageButton) view.findViewById(R.id.delete);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        ticketTypes = tickets.get(position).getTicketTypes();
        final Ticket ticket = tickets.get(position);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                R.layout.spinner_item, getTypes(ticketTypes));

        holder.ticketTypesSpinner.setAdapter(dataAdapter);
//        holder.priceForOneTicket.setText(String.valueOf(tickets.getPrice()));

        Log.d(TAG, String.valueOf(tickets.size()));
        holder.deleteButton.setVisibility(View.VISIBLE);
        if (tickets.size() == 1) {
            holder.deleteButton.setVisibility(View.INVISIBLE);
        }

        holder.ticketTypesSpinner.setSelection(ticket.getSelectedTypePosition());
        holder.numberOfTickets.setText(String.valueOf(ticket.getSelectNumberOfTickets()));
        holder.priceForOneTicket.setText((double)ticketTypes.get(ticket.getSelectedTypePosition()).getTicketPrice() + " лв.");
        calcTotalPrice(holder, position);

        final ViewHolder finalHolder = holder;
        holder.ticketTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int itemPosition, long id) {

//                positionOfSpinner = position;
                ticket.setSelectedTypePosition(itemPosition);

                calcTotalPrice(finalHolder, position);

//                int price = tickets.getPrice() * number;
//                finalHolder.priceForOneTicket.setText(String.valueOf(tickets.getPrice()));
//                finalHolder.totalPrice.setText(String.valueOf(price));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.increaseNumberOfTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementNumberOfSelectedSeats(finalHolder, position);
            }
        });

        holder.decreaseNumberOfTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementNumberOfSelectedSeats(finalHolder, position);
            }
        });


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tickets.size() > 0) {
                    if (tickets.size() == 1) {
                        finalHolder.deleteButton.setVisibility(View.INVISIBLE);
                    } else {
//                        tickets.size()--;
                        tickets.remove(tickets.size() - 1);
                        mListener.delete(tickets.size());
//                        TicketTypesAdapter.this.notifyDataSetChanged();

                    }
                }
            }
        });

        return view;
    }

    private void incrementNumberOfSelectedSeats(ViewHolder holder, int position) {
        Ticket ticket = tickets.get(position);
        if (ticket.getSelectNumberOfTickets() > MAX_OF_SELECTED_SEAT - 1) {
            return;
        }

        ticket.setSelectNumberOfTickets(ticket.getSelectNumberOfTickets() + 1);

        holder.numberOfTickets.setText(String.valueOf(ticket.getSelectNumberOfTickets()));

        calcTotalPrice(holder, position);
//        seatContainer.setNumberOfSelectedSeats(number);
//        seatContainer.dispatchTouchEvent(MotionEvent.obtain(10, 10, MotionEvent.ACTION_MOVE, 450, 100, 0));
    }

    private void decrementNumberOfSelectedSeats(ViewHolder holder, int position) {
        Ticket ticket = tickets.get(position);
        if (ticket.getSelectNumberOfTickets() < 1) {
            return;
        }
        ticket.setSelectNumberOfTickets(ticket.getSelectNumberOfTickets() - 1);
        holder.numberOfTickets.setText(String.valueOf(ticket.getSelectNumberOfTickets()));
        calcTotalPrice(holder, position);
//        tickets.get(position).setSelectNumberOfTickets(number);
//        seatContainer.setNumberOfSelectedSeats(number);
//        seatContainer.dispatchTouchEvent(MotionEvent.obtain(10, 10, MotionEvent.ACTION_MOVE, 450, 100, 0));
    }

    private void calcTotalPrice(ViewHolder holder, int position) {
        double price = ticketTypes.get(tickets.get(position).getSelectedTypePosition()).getTicketPrice() * tickets.get(position).getSelectNumberOfTickets();
        holder.priceForOneTicket.setText((double)ticketTypes.get(tickets.get(position).getSelectedTypePosition()).getTicketPrice() + " лв.");
        holder.totalPrice.setText(price + " лв.");
    }

    public int getNumberOfTickets() {
        Log.d(TAG, String.valueOf(tickets.size()));
        return tickets.size();
    }

    public void addNewTicket(final Ticket ticket) {
        if (tickets.size() < 6) {
//            countOfSelectedTickets++;

            tickets.add(ticket);

            this.notifyDataSetChanged();
        } else {
            return;
        }
    }

    private ArrayList<String> getTypes(ArrayList<TicketType> input) {
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            result.add(input.get(i).getTicketType());
        }

        return result;
    }

    private class ViewHolder {
        Spinner ticketTypesSpinner;
        EditText priceForOneTicket;
        EditText totalPrice;
        TextView numberOfTickets;
        ImageButton increaseNumberOfTickets;
        ImageButton decreaseNumberOfTickets;
        ImageButton deleteButton;
    }

}
