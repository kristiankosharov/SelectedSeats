package com.frt.selectseats;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private SeatViewLayout seatContainer;
    private Button arrowDown;
    private Button arrowUp;
    private int number = 1;
    private TextView numberOfSeats;
    private static final int NUMBERS_OF_ROWS = 10;
    private static final int NUMBER_OF_COLUMNS = 10;
    private Button selectedSeats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final List<Seat> seatList = new ArrayList<Seat>();

        arrowDown = (Button) findViewById(R.id.button_down);
        arrowUp = (Button) findViewById(R.id.button_up);
        numberOfSeats = (TextView) findViewById(R.id.number);
        numberOfSeats.setText(String.valueOf(number));
        selectedSeats = (Button) findViewById(R.id.get_selected_seats);

        for (int i = 0; i < NUMBERS_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
                Seat s = new Seat();

                s.setRow(i);
                s.setColumn(j);

                s.setState(1);

                if (i == 4) {
                    s.setState(Seat.INVISIBLE_SEAT);
                }

                if ((i == 3 && j == 3) || (i == 3 && j == 4) || (i == 7 && j == 4) || (i == 7 && j == 5) || (i == 7 && j == 6)) {
                    s.setState(Seat.HIRED_SEAT);
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

        arrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (number < 2) {
                    return;
                }
                number--;
                numberOfSeats.setText(String.valueOf(number));
                seatContainer.setNumberOfSelectedSeats(number);
//                seatContainer.dispatchTouchEvent(MotionEvent.obtain(10, 10, MotionEvent.ACTION_MOVE, 450, 100, 0));

            }
        });

        arrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (number > NUMBER_OF_COLUMNS) {
                    return;
                }
                number++;
                numberOfSeats.setText(String.valueOf(number));
                seatContainer.setNumberOfSelectedSeats(number);
//                seatContainer.dispatchTouchEvent(MotionEvent.obtain(10, 10, MotionEvent.ACTION_MOVE, 450, 100, 0));
            }
        });

        selectedSeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Seat> selectedSeats = new ArrayList<Seat>();
                for (int i = 0; i < seatList.size(); i++) {
                    if (seatList.get(i).isSelected()) {
                        selectedSeats.add(seatList.get(i));
                        if (seatList.get(i).isOverHiredSeat()) {
                            Toast.makeText(MainActivity.this, "Ne stava bratched", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }

                Toast.makeText(MainActivity.this, selectedSeats.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private View.OnTouchListener listener(final Seat s) {
        View.OnTouchListener listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                s.setState(1);
                return true;
            }
        };
        return listener;
    }
}
