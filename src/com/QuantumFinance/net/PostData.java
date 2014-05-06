package com.QuantumFinance.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.net.base.HttpResponseEntity;
import com.QuantumFinance.net.base.LoginOrRegResult;
import com.QuantumFinance.net.base.PostCommentBase;
import com.QuantumFinance.net.base.RegResult;
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
	private int type; // type =1 评论 2为注册 3登录 4为社交登录 5是理财评估
	private String TAG = "PostData";

	public PostData(Context mContext, Handler mHandler, Object obj, int type) {
		this.mContext = mContext;
		this.mHandler = mHandler;
		switch (type) {
		case 1:
			this.url = AppConstants.HTTPURL.commentpost;
			break;
		case 2:
			this.url = AppConstants.HTTPURL.reg;
			break;
		case 3:
			this.url = AppConstants.HTTPURL.login;
			break;
		case 4:
			this.url = AppConstants.HTTPURL.socialLogin;
			break;
		case 5:
			this.url = AppConstants.HTTPURL.evaluate;
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
		HttpResponseEntity hre = null;
		if (type == 1) {
			PostCommentBase pcb = (PostCommentBase) obj;
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("token", pcb.getToken()));
			params.add(new BasicNameValuePair("user_id", pcb.getUser_id() + ""));
			params.add(new BasicNameValuePair("to_user_id", pcb.getTo_user_id() + ""));
			params.add(new BasicNameValuePair("comment_id", pcb.getComment_id() + ""));
			params.add(new BasicNameValuePair("content", pcb.getContent()));
			hre = HTTP.post(url, params);
		} else {
			hre = HTTP.postByHttpUrlConnection(url, obj);
		}
		switch (hre.getHttpResponseCode()) {
		case 201:
			// 此处为注册的问题
			mHandler.sendEmptyMessage(AppConstants.HANDLER_MESSAGE_NULL);
			break;
		case 422:
			// 此处为注册的问题
			mHandler.sendEmptyMessage(AppConstants.HANDLER_MESSAGE_NULL);
			break;
		case 500:
			// 此处为注册的问题
			mHandler.sendEmptyMessage(AppConstants.HANDLER_MESSAGE_NULL);
			break;
		case 200:
			try {
				String json = StringUtil.byte2String(hre.getB());
				Log.e(TAG, "type=" + type + ";JSON：" + json);
				switch (type) {
				case 1:
					Result result = JSON.parseObject(json, Result.class);
					mHandler.sendMessage(mHandler.obtainMessage(AppConstants.HANDLER_MESSAGE_NORMAL, result));
					break;
				case 2:
					RegResult regResult = JSON.parseObject(json, RegResult.class);
					mHandler.sendMessage(mHandler.obtainMessage(AppConstants.HANDLER_MESSAGE_NORMAL, regResult));
					break;
				case 3:
					LoginOrRegResult.Login login = JSON.parseObject(json, LoginOrRegResult.Login.class);
					mHandler.sendMessage(mHandler.obtainMessage(AppConstants.HANDLER_MESSAGE_NORMAL, login));
					break;
				case 4:
					LoginOrRegResult.Login login2 = JSON.parseObject(json, LoginOrRegResult.Login.class);
					mHandler.sendMessage(mHandler.obtainMessage(AppConstants.HANDLER_MESSAGE_NORMAL, login2));
					break;
				case 5:
					mHandler.sendEmptyMessage(AppConstants.HANDLER_MESSAGE_NORMAL);
					break;
				default:
					break;
				}

			} catch (Exception e) {
				mHandler.sendEmptyMessage(AppConstants.HANDLER_HTTPSTATUS_ERROR);
				Log.e(TAG, "type=" + type + ";问题：", e);
			}
			break;
		default:
			mHandler.sendEmptyMessage(AppConstants.HANDLER_HTTPSTATUS_ERROR);
			Log.d(TAG, "type=" + type + ";问题：" + hre.getHttpResponseCode());
			break;
		}
	}
}
