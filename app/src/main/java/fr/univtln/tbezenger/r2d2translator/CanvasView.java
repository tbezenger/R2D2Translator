package fr.univtln.tbezenger.r2d2translator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by tbezenger on 10/01/2018.
 */
public class CanvasView extends View{

    private final int paintColor = Color.GREEN;
    private Paint drawPaint;
    private Canvas canvas;
    private int height, width;

    private boolean b;

    private short[] spectre;

    private boolean drawing = false;

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
    }

    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        height = canvas.getHeight()/2;
        width = canvas.getWidth();

        canvas.drawColor(Color.WHITE);

        Paint redPaint = new Paint();
        redPaint.setColor(Color.RED);
        Paint greenPaint = new Paint();
        greenPaint.setColor(Color.GREEN);

        if (spectre != null)
            if (b) {
                canvas.drawCircle(40, height, 25, greenPaint);
            }
            else {
                canvas.drawCircle(40, height, 25, redPaint);
            }

            if (drawing)
                for (int i=0; i<spectre.length; i++) {
                    canvas.drawLine(width, height, width-1, (float) (spectre[i]*0.02)+height, drawPaint);
                    width--;
                }
    }

    public void setSpectre(short[] spectre) {
        drawing = true;
        this.spectre = spectre;
        invalidate();
    }
}
