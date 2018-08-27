package com.hc.android.huixin.view;

import com.hc.android.huixin.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

public class LineGridView extends GridView {

	public LineGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public LineGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public LineGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	int ViewWidth = 0;
	int ViewRight = 0;

	@Override
	protected void dispatchDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.dispatchDraw(canvas);

		View localView1 = getChildAt(getFirstVisiblePosition());

		// int column = getWidth() / localView1.getWidth();

		int column = 3;
		int childCount = getChildCount();
		Paint localPaint;
		localPaint = new Paint();
		localPaint.setStyle(Paint.Style.STROKE);
		localPaint.setStrokeWidth(1);
		// localPaint.setColor(getContext().getResources().getColor(R.color.black));
		localPaint.setColor(0xffdcdcdc);
		for (int i = 0; i < childCount; i++) {

			View cellView = getChildAt(i);

			if (i == 0) {

				ViewWidth = cellView.getLeft();
				ViewRight = cellView.getRight() - (getWidth() / 3);
	
			}

			if ((i + 1) % column == 0) {
				canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight() + ViewWidth,
						cellView.getBottom(), localPaint);
			} else if ((i + 1) > (childCount - (childCount % column))) {
				// 貌似column=3 不执行
				// 控制没有满足3个的 竖线偏移
				// canvas.drawLine(cellView.getRight(),
				// cellView.getTop()-ViewWidth*, cellView.getRight(),
				// cellView.getBottom(), localPaint);
				canvas.drawLine(cellView.getRight() - ViewRight, cellView.getTop() - (ViewWidth),
						cellView.getRight() - ViewRight, cellView.getBottom(), localPaint);

				// 控制没有满足3个的 横线偏移
				canvas.drawLine(cellView.getLeft() - ViewWidth, cellView.getBottom(), cellView.getRight(),
						cellView.getBottom(), localPaint);

			} else {

				if (cellView.getRight() == ViewRight + (getWidth() / 3)) {
					canvas.drawLine(cellView.getRight() - ViewRight, cellView.getTop() - (ViewWidth),
							cellView.getRight() - ViewRight, cellView.getBottom(), localPaint);
				
				} else {
					canvas.drawLine(cellView.getRight() + ViewRight, cellView.getTop() - (ViewWidth),
							cellView.getRight() + ViewRight, cellView.getBottom(), localPaint);
				
				}
				canvas.drawLine(cellView.getLeft() - ViewWidth, cellView.getBottom(), cellView.getRight(),
						cellView.getBottom(), localPaint);
	
			}
		}
		if (childCount % column != 0) {
			for (int j = 0; j < (column - childCount % column); j++) {
				View lastView = getChildAt(childCount - 1);
				// canvas.drawLine(lastView.getRight() + lastView.getWidth() *
				// j, lastView.getTop(), lastView.getRight() +
				// lastView.getWidth()* j, lastView.getBottom(), localPaint);
			}
		}

	}

}
