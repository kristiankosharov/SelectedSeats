package com.frt.selectseats;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private SeatViewLayout seatContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Seat> seatList = new ArrayList<Seat>();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
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
        seatContainer.setRows(10);
        seatContainer.setColumns(10);
        seatContainer.setBackgroundColor(Color.TRANSPARENT);
        seatContainer.setSeatDrawableResource(R.drawable.free_seat);
        seatContainer.setHiredSeatDrawableResource(R.drawable.hired_seat);
        seatContainer.setNumberOfSelectedSeats(6);

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
