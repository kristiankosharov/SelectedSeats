package com.frt.selectseats;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.List;

import adapters.TicketTypesAdapter;
import interfaces.DeleteTicket;
import models.Seat;
import models.Ticket;
import models.TicketType;
import views.SeatViewLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DeleteTicket {

    private static final String TAG = MainActivity.class.getName();
    private SeatViewLayout seatContainer;
    private int number = 1;
    //    private TextView numberOfSeats;
    private static final int NUMBERS_OF_ROWS = 10;
    private static final int NUMBER_OF_COLUMNS = 10;
    private Button selectedSeats;
    //    private CheckBox checkBoxIsChild;
//    private CheckBox checkBoxIsInvalid;
    private boolean isChild;
    private boolean isInvalid;
    private Spinner ticketType;
    private EditText priceForOneTicket;
    private EditText totalPrice;
    private List<Seat> seatList;
    private ListView listViewTypes;
    private FrameLayout addNewTicketsButton;
    ArrayList<Ticket> tickets;
    ArrayList<TicketType> ticketsType;
    private String[] types = {"2D детски", "2D пенсионер", "2D нормален", "2D студентски"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seatList = new ArrayList<Seat>();

        initializeViews();
        ImageView floatingActionButton = new ImageView(this);
        floatingActionButton.setImageResource(R.drawable.settings);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this).setContentView(floatingActionButton).setBackgroundDrawable(R.drawable.button_action_blue).setPosition(FloatingActionButton.POSITION_BOTTOM_RIGHT).build();

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        ImageView imageView1 = new ImageView(this);
        ImageView imageView2 = new ImageView(this);
        ImageView imageView3 = new ImageView(this);

        imageView1.setImageDrawable(getResources().getDrawable(R.drawable.settings));
        imageView2.setImageDrawable(getResources().getDrawable(R.drawable.settings));
        imageView3.setImageDrawable(getResources().getDrawable(R.drawable.settings));


        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(itemBuilder.setContentView(imageView1).build())
                .addSubActionView(itemBuilder.setContentView(imageView2).build())
                .addSubActionView(itemBuilder.setContentView(imageView3).build())
                .attachTo(actionButton)
                .build();

        for (int i = 0; i < NUMBERS_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                Seat s = new Seat();

                s.setRow(i);
                s.setColumn(j);

                s.setState(1);

                if (i == 4) {
                    s.setState(Seat.INVISIBLE_SEAT);
                    s.setRow(-1);
                    s.setColumn(-1);
                }

                if ((i == 3 && j == 3) || (i == 3 && j == 4) || (i == 7 && j == 4) || (i == 7 && j == 5) || (i == 7 && j == 6)) {
                    s.setState(Seat.HIRED_SEAT);
                }

                if ((i == 0 && j == 4) || (i == 0 && j == 5) || (i == 0 && j == 6)) {
                    s.setState(Seat.INVALID_SEAT);
                }

                if (i == 1) {
                    s.setState(Seat.CHILDREN_SEAT);

                }

                seatList.add(s);
            }
        }

        seatContainer = (SeatViewLayout) findViewById(R.id.rl_SeatContainer);
        seatContainer.setContext(this);
        seatContainer.setSeatList(new ArrayList<Seat>(seatList));
        seatContainer.setRows(NUMBERS_OF_ROWS);
        seatContainer.setColumns(NUMBER_OF_COLUMNS);
        seatContainer.setBackgroundColor(Color.TRANSPARENT);
        seatContainer.setSeatDrawableResource(R.drawable.free_seat);
        seatContainer.setHiredSeatDrawableResource(R.drawable.hired_seat);
        seatContainer.setNumberOfSelectedSeats(number);

//        checkBoxIsChild.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    isChild = true;
//                } else {
//                    isChild = false;
//                }
//                seatContainer.setIsForChildren(isChild);
//            }
//        });

//        checkBoxIsInvalid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    isInvalid = true;
//                } else {
//                    isInvalid = false;
//                }
//                seatContainer.setIsForInvalid(isInvalid);
//            }
//        });

        tickets = new ArrayList<>();

        ticketsType = new ArrayList<>();

        for (int i = 0; i < types.length; i++) {
            TicketType ticketType = new TicketType();
            ticketType.setTicketPrice(i * 2);
            ticketType.setTicketType(types[i]);
            ticketsType.add(ticketType);
        }

        Ticket ticket = new Ticket();
        ticket.setTicketTypes(ticketsType);
        tickets.add(ticket);

        TicketTypesAdapter adapter = new TicketTypesAdapter(this, tickets);
        listViewTypes.setAdapter(adapter);

    }

    private void initializeViews() {
//        numberOfSeats = (TextView) findViewById(R.id.number);
//        numberOfSeats.setText(String.valueOf(number));
        selectedSeats = (Button) findViewById(R.id.get_selected_seats);
//        checkBoxIsChild = (CheckBox) findViewById(R.id.checkbox_child);
//        checkBoxIsInvalid = (CheckBox) findViewById(R.id.checkbox_invalid);
        ticketType = (Spinner) findViewById(R.id.ticket_type);
        priceForOneTicket = (EditText) findViewById(R.id.price_for_one);
        totalPrice = (EditText) findViewById(R.id.total_price);
        listViewTypes = (ListView) findViewById(R.id.list_view_ticket_type);
        addNewTicketsButton = (FrameLayout) findViewById(R.id.add_new_ticket);

        int size =
                getResources().getDimensionPixelSize(com.oguzdev.circularfloatingactionmenu.library.R.dimen.action_button_size);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
        params.addRule(RelativeLayout.BELOW, listViewTypes.getId());
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        addNewTicketsButton.setLayoutParams(params);


//        checkBoxIsInvalid.setChecked(false);
//        checkBoxIsInvalid.setChecked(false);
        selectedSeats.setOnClickListener(this);
        addNewTicketsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch (viewId) {
            case R.id.get_selected_seats:
                getSelectedSeats();
                break;
            case R.id.add_new_ticket:

                Ticket ticket = new Ticket();
                ticket.setTicketTypes(ticketsType);

                ((TicketTypesAdapter) listViewTypes.getAdapter()).addNewTicket(ticket);

                int height = ((TicketTypesAdapter) listViewTypes.getAdapter()).getNumberOfTickets();

                int dimen = (int) getResources().getDimension(R.dimen.reservation_of_ticket_height);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height * dimen);
                listViewTypes.setLayoutParams(params);

                break;
        }
    }


    private ArrayList<Seat> getSelectedSeats() {
        ArrayList<Seat> selectedSeats = new ArrayList<Seat>();
        for (int i = 0; i < seatList.size(); i++) {
            if (seatList.get(i).isSelected()) {
                selectedSeats.add(seatList.get(i));
                if (seatList.get(i).isOverHiredSeat()) {
                    Toast.makeText(MainActivity.this, "No way, some of seats were hired!", Toast.LENGTH_LONG).show();
                    return null;
                }
            }
        }

        Toast.makeText(MainActivity.this, selectedSeats.toString(), Toast.LENGTH_LONG).show();

        return selectedSeats;
    }


    @Override
    public void delete(int numberOfTickets) {

        int dimen = (int) getResources().getDimension(R.dimen.reservation_of_ticket_height);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, numberOfTickets * dimen);
        View view = listViewTypes.getChildAt(numberOfTickets);
        listViewTypes.removeViewInLayout(view);
        listViewTypes.setLayoutParams(params);
    }
}
