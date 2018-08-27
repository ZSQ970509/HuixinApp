package com.hc.android.huixin.view;

import java.util.Timer;
import java.util.TimerTask;

import com.hc.android.huixin.MainActivity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class GuideGallery extends Gallery {

	private MainActivity m_iact;

	public GuideGallery(Context context) {
		super(context);
	}

	public GuideGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GuideGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setMainActivity(MainActivity iact) {
		m_iact = iact;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		if (e1 == null || e2 == null) {
			return false;
		}
		int kEvent;
		if (isScrollingLeft(e1, e2)) { // Check if scrolling left
			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
		} else { // Otherwise scrolling right
			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(kEvent, null);
		if (this.getSelectedItemPosition() == 0) {
			this.setSelection(ImageAdapter.imgs.length);
		}
		new Timer().schedule(new TimerTask() {
			public void run() {
				m_iact.timeFlag = false;
				this.cancel();
			}
		}, 2000);
		return true;

	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		return e2.getX() > e1.getX();

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		m_iact.timeTaks.timeCondition = false;
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

}
