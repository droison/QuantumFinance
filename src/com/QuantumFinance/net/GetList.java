package com.QuantumFinance.net;

import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.net.base.HttpResponseEntity;
import com.QuantumFinance.util.StringUtil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

public class GetList implements Runnable {
	private Context mContext;
	private Handler mHandler;
	private String url;
	private int type;// 1为壁纸，2为锁屏，3为视频,4为评论,5为PPT，6为返回的热门标签
	private String TAG = "GetList";

	public GetList(Context mContext, Handler mHandler, String url, int type) {
		this.mContext = mContext;
		this.mHandler = mHandler;
		this.url = url;
		this.type = type;
	}

	public void run() {

		Boolean b = false;
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			b = networkInfo.isAvailable();
		}
		if (!b) {
			mHandler.sendEmptyMessage(AppConstants.HANDLER_MESSAGE_NONETWORK);
			return;
		}

		HttpResponseEntity hre = HTTP.get(url);
		if(hre == null){
			mHandler.sendEmptyMessage(AppConstants.HANDLER_HTTPSTATUS_ERROR);
			Log.e(TAG,"type="+type+";hre＝null");
			return;
		}
		switch (hre.getHttpResponseCode()) {
		case 200:
			try {
				String json = StringUtil.byte2String(hre.getB());
				Log.e(TAG, "type="+type+";JSON：" + json);
				switch (type) {
				case 1:
					break;
				}

			} catch (Exception e) {
				mHandler.sendEmptyMessage(AppConstants.HANDLER_HTTPSTATUS_ERROR);
				Log.e("StringGet", "200", e);
			}
			break;
		default:
			mHandler.sendEmptyMessage(AppConstants.HANDLER_HTTPSTATUS_ERROR);
			Log.d("StringGet", "" + hre.getHttpResponseCode());
			break;
		}
	}

}
