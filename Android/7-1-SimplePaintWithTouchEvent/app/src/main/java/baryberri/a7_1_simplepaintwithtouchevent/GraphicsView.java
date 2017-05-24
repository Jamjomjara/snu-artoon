package baryberri.a7_1_simplepaintwithtouchevent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class GraphicsView extends View {
    private int startX = -1;
    private int startY = -1;
    private int endX = -1;
    private int endY = -1;
    private Paint paint = new Paint();

    public GraphicsView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int)event.getX();
                startY = (int)event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                endX = (int)event.getX();
                endY = (int)event.getY();
                this.invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);

        switch (MainActivity.currentShape) {
            case MainActivity.LINE:
                canvas.drawLine(startX, startY, endX, endY, paint);
                break;

            case MainActivity.CIRCLE:
                int radius = (int)Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
                canvas.drawCircle(startX, startY, radius, paint);
                break;
        }
    }
}
