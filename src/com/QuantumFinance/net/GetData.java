package com.QuantumFinance.net;

import java.util.List;

import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.net.base.CommentBase;
import com.QuantumFinance.net.base.HistoryBase;
import com.QuantumFinance.net.base.HttpResponseEntity;
import com.QuantumFinance.net.base.PPTBase;
import com.QuantumFinance.net.base.PaperBase;
import com.QuantumFinance.net.base.RecommendBase;
import com.QuantumFinance.net.base.RecommendInfoBase;
import com.QuantumFinance.net.base.Result;
import com.QuantumFinance.util.StringUtil;
import com.alibaba.fastjson.JSON;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

public class GetData implements Runnable {
	private Activity mContext;
	private Handler mHandler;
	private String url;
	private int type;// 1为稳健型，2为激进型，3为推荐页PPT，4是获取文章列表,5是推广详情，6是文章详情，7是文章评论，8是文章赞，9是历史回顾
	private String TAG = "GetList";

	public GetData(Activity mContext, Handler mHandler, String url, int type) {
		this.mContext = mContext;
		this.mHandler = mHandler;
		this.url = url;
		switch (type) {
		case 1:
			this.url = AppConstants.HTTPURL.wenjian;
			break;
		case 2:
			this.url = AppConstants.HTTPURL.jijin;
			break;
		case 3:
			this.url = AppConstants.HTTPURL.ppt;
			break;
		}
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
		if (hre == null) {
			mHandler.sendEmptyMessage(AppConstants.HANDLER_HTTPSTATUS_ERROR);
			Log.e(TAG, "type=" + type + ";hre＝null");
			return;
		}
		switch (hre.getHttpResponseCode()) {
		case 200:
			try {
				String json = StringUtil.byte2String(hre.getB());
				Log.e(TAG, "type=" + type + ";JSON：" + json);
				switch (type) {
				case 1:
				case 2:
					List<RecommendBase> rb = JSON.parseArray(json, RecommendBase.class);
					mHandler.sendMessage(mHandler.obtainMessage(AppConstants.HANDLER_MESSAGE_NORMAL, rb));
					break;
				case 3:
					List<PPTBase> pbs = JSON.parseArray(json, PPTBase.class);
					mHandler.sendMessage(mHandler.obtainMessage(AppConstants.HANDLER_MESSAGE_NORMAL, pbs));
					break;
				case 4:
					List<PaperBase> pb = JSON.parseArray(json, PaperBase.class);
					mHandler.sendMessage(mHandler.obtainMessage(AppConstants.HANDLER_MESSAGE_NORMAL, pb));
					break;
				case 5:
					RecommendInfoBase rib = JSON.parseObject(json, RecommendInfoBase.class);
					mHandler.sendMessage(mHandler.obtainMessage(AppConstants.HANDLER_MESSAGE_NORMAL, rib));
					break;
				case 6:
					PaperBase pbi = JSON.parseObject(json, PaperBase.class);
					mHandler.sendMessage(mHandler.obtainMessage(AppConstants.HANDLER_MESSAGE_NORMAL, pbi));
					break;
				case 7:
					List<CommentBase> cb = JSON.parseArray(json, CommentBase.class);
					mHandler.sendMessage(mHandler.obtainMessage(AppConstants.HANDLER_MESSAGE_NORMAL, cb));
					break;
				case 8:
					Result result = JSON.parseObject(json,Result.class);
					mHandler.sendMessage(mHandler.obtainMessage(AppConstants.HANDLER_MESSAGE_NORMAL, result));
					break;
				case 9:
					List<HistoryBase> hb = JSON.parseArray(json, HistoryBase.class);
					mHandler.sendMessage(mHandler.obtainMessage(AppConstants.HANDLER_MESSAGE_NORMAL, hb));
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
