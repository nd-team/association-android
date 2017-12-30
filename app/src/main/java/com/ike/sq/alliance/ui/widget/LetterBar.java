package com.ike.sq.alliance.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by T-BayMax on 2017/5/15.
 */

public class LetterBar extends View {
    public interface SelectedListener {
        void onSelected(String letter);

        void onCancel();
    }

    private SelectedListener selectedListener;
    public String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z","#"};
    private int averageH;

    public LetterBar(Context context) {
        super(context);
    }

    public LetterBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LetterBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(0xff888888);
        paint.setTextSize(36);
        paint.setFakeBoldText(true);

        int w = getWidth();
        int h = getHeight();
        averageH = h / letters.length;


        for (int n = 0; n < letters.length; n++) {
            float drawX = w / 2f - paint.measureText((letters[n])) / 2f;
            canvas.drawText(letters[n], drawX, averageH * (n + 1), paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                y = event.getY();
                if (selectedListener != null && y > 0 && y < getHeight()) {
                    selectedListener.onSelected(letters[(int) y / averageH]);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (selectedListener != null) {
                    selectedListener.onCancel();
                }
                break;
        }
        return true;
    }

    public void setSelectedListener(SelectedListener listener) {
        selectedListener = listener;
    }
}
