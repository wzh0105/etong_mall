package cn.xiaocool.android_etong.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import cn.xiaocool.android_etong.R;



public class TimeTextView extends TextView implements Runnable {
	Paint mPaint; // ����,�����˻�����ͼ�Ρ��ı��ȵ���ʽ����ɫ��Ϣ
	private int[] times;
	private long mday, mhour, mmin, msecond;// �죬Сʱ�����ӣ���
	private boolean run = false; // �Ƿ�������

	public TimeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.TimeTextView);
		array.recycle(); // һ��Ҫ���ã�������ε��趨����´ε�ʹ�����Ӱ��
	}

	public TimeTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mPaint = new Paint();
		TypedArray array = context.obtainStyledAttributes(attrs,
				R.styleable.TimeTextView);
		array.recycle(); // һ��Ҫ���ã�������ε��趨����´ε�ʹ�����Ӱ��
	}

	public TimeTextView(Context context) {
		super(context);
	}

	public int[] getTimes() {
		return times;
	}

	public void setTimes(int[] times) {
		this.times = times;
		mday = times[0];
		mhour = times[1];
		mmin = times[2];
		msecond = times[3];
	}

	/**
	 * ����ʱ����
	 */
	private void ComputeTime() {
		msecond--;
		if (msecond < 0) {
			mmin--;
			msecond = 59;
			if (mmin < 0) {
				mmin = 59;
				mhour--;
				if (mhour < 0) {
					// ����ʱ����
					mhour = 59;
					mday--;
				}
			}
		}
	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	@Override
	public void run() {
		// ��ʾ�Ѿ�����
		run = true;

		ComputeTime();
		String strTime = mday + ":" + mhour + ":" + mmin + ":"
				+ msecond ;
		this.setText(strTime);
		if (mday <= 0 && mhour == 0 && mmin == 0 && msecond == 0) {
			return;
		}
		postDelayed(this, 1000);
	}
}
