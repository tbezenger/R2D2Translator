package fr.univtln.tbezenger.r2d2translator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tbezenger on 10/01/2018.
 */
public class CanvasView extends View{

    // setup initial color
    private final int paintColor = Color.GREEN;
    // defines paint and canvas
    private Paint drawPaint;
    private Canvas canvas;
    private int height, width;

    // tableau contenant le spectre a dessiner
    private short[] spectre;

    private boolean drawing = false;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
    }

    // Setup paint with color and stroke styles
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

        if (spectre != null)
            if (drawing)
                for (int i=0; i<spectre.length; i++) {
                    canvas.drawLine(width, height, width-1, (float) (spectre[i]*0.01)+height, drawPaint);
                    width--;
                }
    }

    public void setSpectre(short[] spectre) {

        drawing = true;
        this.spectre = spectre;
        invalidate();
    }
}
