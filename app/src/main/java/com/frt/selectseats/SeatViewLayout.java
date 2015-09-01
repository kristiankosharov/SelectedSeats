package com.frt.selectseats;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Source:http://stackoverflow.com/questions/13864480/android-how-to-circular-zoom-magnify-part-of-image
 * http://stackoverflow.com/questions/11442934/magnifying-part-of-the-canvas-when-touched
 */
public class SeatViewLayout extends LinearLayout {

    private static final String TAG = SeatViewLayout.class.getName();
    private ShapeDrawable mSeatDrawable;
    private Context context;
    private static int width = 10;
    private static int height = 10;
    private static int x = 13;
    private static int y = 13;

    private int seatDrawableResource = -1;
    private int hiredSeatDrawableResource = -1;
    private int selectedDrawableResource = R.drawable.selected_seat;

    ArrayList<View> allSeats = new ArrayList<>();
    int[] location = new int[2];

    private int columns = 0;
    private int rows = 0;

    private Canvas canvas;
    private int numberOfSelectedSeats = 0;

    Matrix matrix = new Matrix();
    Paint shaderPaint = new Paint();
    boolean zooming;
    boolean isFirst = true;
    boolean isFirstDraw = true;
    static final PointF zoomPos = new PointF(0, 0);

    BitmapShader shader = null;
    private Bitmap circularBitmap;
    private int seatWidth = 50;
    private int seatHeight = 50;

    private int seatRange = 60;

    List<Seat> seatList = new ArrayList<Seat>();

    public SeatViewLayout(Context context) {
        super(context);
        this.context = context;
        this.setOrientation(VERTICAL);
        this.setWeightSum(rows);
    }

    public SeatViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setOrientation(VERTICAL);
        this.setWeightSum(rows);
    }

    public SeatViewLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        this.setOrientation(VERTICAL);
        this.setWeightSum(rows);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setSeatList(ArrayList<Seat> seatList) {
        this.seatList = seatList;
    }

    public int getRows() {
        return this.rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getSeatDrawableResource() {
        return this.seatDrawableResource;
    }

    public void setSeatDrawableResource(int seatDrawableResource) {
        this.seatDrawableResource = seatDrawableResource;
    }

    public int getHiredSeatDrawableResource() {
        return this.hiredSeatDrawableResource;
    }

    public void setHiredSeatDrawableResource(int hiredSeatDrawableResource) {
        this.hiredSeatDrawableResource = hiredSeatDrawableResource;
    }


    public int getNumberOfSelectedSeats() {
        return this.numberOfSelectedSeats;
    }

    public void setNumberOfSelectedSeats(int numberOfSelectedSeats) {
        if (numberOfSelectedSeats < 1) {
            return;
        }
        if (numberOfSelectedSeats > columns) {
            return;
        }

        if (numberOfSelectedSeats == 1) {
            this.numberOfSelectedSeats = 0;
        } else {
            this.numberOfSelectedSeats = numberOfSelectedSeats - 1;
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isFirstDraw) {
            isFirstDraw = false;
            addSeatViews();
        }

        if (zooming) {
            matrix.reset();
            matrix.postScale(1.7f, 1.7f, zoomPos.x, zoomPos.y);
            shaderPaint.getShader().setLocalMatrix(matrix);
            canvas.drawCircle(zoomPos.x, zoomPos.y - 50, 250, shaderPaint);
        }


    }

    private void addSeatViews() {
        LinearLayout row = null;
        ArrayList<Seat> seatsInRow = null;
        ImageView seatView = null;
        LayoutParams params = new LayoutParams(seatWidth, seatHeight);
        TextView rowNumber = null;
        params.setMargins(5, 5, 5, 5);

        for (int i = 0; i < rows; i++) {
            row = new LinearLayout(context);
            row.setOrientation(HORIZONTAL);
            row.setWeightSum(columns + 1);
            row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, seatHeight));

            for (int j = 0; j < columns; j++) {

                if (j == 0) {
                    rowNumber = new TextView(context);
                    rowNumber.setText(String.valueOf(i + 1));
                    rowNumber.setLayoutParams(params);
                    row.addView(rowNumber);
                }

                seatView = new ImageView(context);
                seatView.setLayoutParams(params);
                seatView.setBackgroundColor(Color.TRANSPARENT);

                fillRightImagesForSeats(seatView, j, i);
                row.addView(seatView);
                allSeats.add(seatView);
            }
            this.addView(row);
        }
    }

    private void fillRightImagesForSeats(View seatView, int columns, int rows) {
        for (int k = 0; k < seatList.size(); k++) {
            if (seatList.get(k).getColumn() == columns && seatList.get(k).getRow() == rows) {
                Seat seat = seatList.get(k);
                Log.d(TAG, "Seat state: " + seat.getState());
                if (seat.getState() == Seat.FREE_SEAT) {
                    if (seatDrawableResource != -1 && seatDrawableResource != 0) {
                        seatView.setBackgroundResource(seatDrawableResource);
                    }
                } else if (seat.getState() == Seat.HIRED_SEAT) {
                    if (hiredSeatDrawableResource != -1 && hiredSeatDrawableResource != 0) {
                        seatView.setBackgroundResource(hiredSeatDrawableResource);
                    }
                } else {
                    seatView.setVisibility(View.INVISIBLE);
                }

            }
        }

    }

    private OnClickListener getSeatFocusChangeListener() {
        OnClickListener seatListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(TAG, "On Click Listener");
            }
        };

        return seatListener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        if (isFirst) {
            shader = new BitmapShader(getBitmapFromView(this), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            isFirst = false;
        }

        shaderPaint.setShader(shader);

        if (circularBitmap != null) {
            circularBitmap.recycle();
            circularBitmap = null;
        }

        Log.d("touchhhhhhhhhhhhhhhh", "touch" + zoomPos.x + "," + zoomPos.y);
        matrix.reset();
        matrix.postScale(1.5f, 1.5f);
        matrix.postTranslate(-zoomPos.x, -zoomPos.y);
        shader.setLocalMatrix(matrix);

        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                zoomPos.x = event.getX();
                zoomPos.y = event.getY();

                if (numberOfSelectedSeats < 3) {
                    zooming = true;
                }

                for (int i = 0; i < seatList.size(); i++) {

                    final View seat = allSeats.get(i);

                    if (zoomPos.x > (seatList.get(i).getColumn() * seatRange)
                            && zoomPos.x < ((seatList.get(i).getColumn() + 1) * 60 + (numberOfSelectedSeats * seatRange))
                            && zoomPos.y > seatList.get(i).getRow() * seatRange
                            && zoomPos.y < (seatList.get(i).getRow() + 1) * seatRange) {

                        if (seatList.get(i).getState() == Seat.HIRED_SEAT) {
                            seat.setBackgroundResource(R.drawable.error_seat);
                        } else {
                            seat.setBackgroundResource(selectedDrawableResource);
                            shader = new BitmapShader(getBitmapFromView(this), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                        }

                    } else {
                        if (seatList.get(i).getState() == Seat.HIRED_SEAT) {
                            seat.setBackgroundResource(hiredSeatDrawableResource);
                        } else {
                            seat.setBackgroundResource(seatDrawableResource);
                        }
                    }
                }

                this.invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                zooming = false;
                this.invalidate();
                break;
        }


        return true;
    }

    private void drawCanvas(Canvas canvas, Seat seat, int color) {
        int row = seat.getRow();
        int column = seat.getColumn();

        int new_x = x * row;
        int new_y = y * column;

        View view = new View(getContext());


        mSeatDrawable = new ShapeDrawable(new RectShape());
        mSeatDrawable.getPaint().setColor(color);
        mSeatDrawable.setBounds(new_x, new_y, new_x + width, new_y + height);
        mSeatDrawable.draw(canvas);
    }

    /**
     * http://stackoverflow.com/questions/5536066/convert-view-to-bitmap-on-android
     *
     * @param view
     * @return bitmap
     */
    public Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
//        circularBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
//
//        //Bind a canvas to it
//        Canvas canvas = new Canvas(circularBitmap);
//        //Get the view's background
//        Drawable bgDrawable = view.getBackground();
//        if (bgDrawable != null) {
//            //has background drawable, then draw it on the canvas
//            bgDrawable.draw(canvas);
//        } else {
//            //does not have background drawable, then draw white background on the canvas
//            canvas.drawColor(Color.WHITE);
//        }
//        // draw the view on the canvas
//        view.draw(canvas);
        //return the bitmap

//        view.setBackgroundColor(getResources().getColor(R.color.background_zoom_view));

        view.setDrawingCacheEnabled(true);

        view.buildDrawingCache();

        Bitmap bm = view.getDrawingCache();
//        view.setBackgroundColor(Color.TRANSPARENT);

        return bm;
    }
}