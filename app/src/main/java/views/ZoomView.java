package views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Kristian Kosharov on 3.9.2015 г..
 */
public class ZoomView extends View {

    private Matrix matrix = new Matrix();
    private PointF zoomPos = new PointF(0, 0);
    private Paint shaderPaint = new Paint();
    private BitmapShader shader = null;
    private boolean isFirst = true;
    private Bitmap bm;

    public ZoomView(Context context, PointF zoomPos, Bitmap bm) {
        super(context);
        this.zoomPos = zoomPos;
        this.bm = bm;
    }

    public ZoomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZoomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isFirst) {
            shader = new BitmapShader(bm, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            isFirst = false;
            shaderPaint.setShader(shader);
        }


        matrix.reset();
        matrix.postScale(1.7f, 1.7f, zoomPos.x, zoomPos.y);
        shaderPaint.getShader().setLocalMatrix(matrix);
        canvas.drawRect(0, 0, 600, 400, shaderPaint);
//        canvas.drawCircle(zoomPos.x, zoomPos.y, 150, shaderPaint);
    }
}
