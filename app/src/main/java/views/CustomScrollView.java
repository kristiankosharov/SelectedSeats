package views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by Kristian Kosharov on 2.9.2015 г..
 */
public class CustomScrollView extends ScrollView {

    private boolean isScrollable;

    public boolean isScrollable() {
        return isScrollable;
    }

    public void setIsScrollable(boolean isScrollable) {
        this.isScrollable = isScrollable;
    }

    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (!isScrollable) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }
}
