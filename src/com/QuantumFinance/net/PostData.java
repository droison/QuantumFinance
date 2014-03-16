package com.QuantumFinance.net;

import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.net.base.HttpResponseEntity;
import com.QuantumFinance.net.base.Result;
import com.QuantumFinance.util.StringUtil;
import com.alibaba.fastjson.JSON;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

public class PostData implements Runnable {
	private Context mContext;
	private Handler mHandler;
	private String url;
	private Object obj;
	private int type; //type =1 评论
	private String TAG = "PostData";

	public PostData(Context mContext, Handler mHandler, Object obj, int type) {
		this.mContext = mContext;
		this.mHandler = mHandler;
		switch (type) {
		case 1:
			this.url = AppConstants.HTTPURL.commentpost;
			break;
		default:
			break;
		}
		this.type = type;
		this.obj = obj;
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

		HttpResponseEntity hre = HTTP.postByHttpUrlConnection(url, obj);
		switch (hre.getHttpResponseCode()) {
		case 200:
			try {
				String json = StringUtil.byte2String(hre.getB());
				Log.e(TAG, "type="+type+";JSON：" + json);
				switch (type) {
				case 1:
					Result result = JSON.parseObject(json,Result.class);
					mHandler.sendMessage(mHandler.obtainMessage(AppConstants.HANDLER_MESSAGE_NORMAL, result));
					break;
				default:
					break;
				}

			} catch (Exception e) {
				mHandler.sendEmptyMessage(AppConstants.HANDLER_HTTPSTATUS_ERROR);
				Log.e(TAG, "type="+type+";问题：",e);
			}
			break;
		default:
			mHandler.sendEmptyMessage(AppConstants.HANDLER_HTTPSTATUS_ERROR);
			Log.d(TAG, "type="+type+";问题："+hre.getHttpResponseCode());
			break;
		}
	}

}
