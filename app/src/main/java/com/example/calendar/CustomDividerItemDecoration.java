package com.example.calendar;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class CustomDividerItemDecoration extends RecyclerView.ItemDecoration {
    private final Paint paint;

    public CustomDividerItemDecoration(int color, int height) {
        paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(height);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < parent.getChildCount() - 1; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + (int) paint.getStrokeWidth();

            c.drawLine(left, top, right, bottom, paint);
        }
    }
}