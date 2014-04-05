package com.QuantumFinance.util;

import android.content.Context;

public class DpSpDip2Px {

	float scale;
	float fontScale;
	int screenPxWidth;
	float screenDpWidth;
	float screenDpHigh;

	public DpSpDip2Px(Context context) {
		scale = context.getResources().getDisplayMetrics().density;
		fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		screenPxWidth = context.getResources().getDisplayMetrics().widthPixels;
		screenDpHigh = context.getResources().getDisplayMetrics().heightPixels;
	}

	// 这是dp
	public int getScreenDpWidth() {
		return px2dip(screenPxWidth);
	}

	public int getPPTHigh() {
		return screenPxWidth * 350 / 720;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public int dip2px(float dpValue) {
		return (int) (dpValue * scale + 0.5f);
	}

	public int px2dip(float pxValue) {
		return (int) (pxValue / scale + 0.5f);
	}

	public int px2sp(float pxValue) {
		return (int) (pxValue / fontScale + 0.5f);
	}

	public int sp2px(float spValue) {
		return (int) (spValue * fontScale + 0.5f);
	}

	public int sp2dp(float spValue) {
		float pxValue = spValue * fontScale + 0.5f;
		return (int) (pxValue / scale + 0.5f);
	}

}
