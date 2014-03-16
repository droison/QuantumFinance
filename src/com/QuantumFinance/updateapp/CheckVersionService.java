
package com.QuantumFinance.updateapp;

import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.net.HTTP;
import com.QuantumFinance.net.base.HttpResponseEntity;
import com.QuantumFinance.util.StringUtil;
import com.alibaba.fastjson.JSON;

import android.content.Context;
import android.os.Handler;

public class CheckVersionService implements Runnable {

    private Handler mHandler;

    private Context context;

    public CheckVersionService(Context context,Handler mHandler) {
        this.mHandler = mHandler;
        this.context = context;
    }

    public void run() {

    		HttpResponseEntity hre = HTTP.get(AppConstants.HTTPURL.checkVersion);
    		if(hre == null){
    			mHandler.sendEmptyMessage(AppConstants.HANDLER_HTTPSTATUS_ERROR);
    			return;
    		}
    		switch (hre.getHttpResponseCode()) {
    		case 200:
    			try {
    				String json = StringUtil.byte2String(hre.getB());
    				CheckVersionBase cvb = (CheckVersionBase) JSON.parseObject(json, CheckVersionBase.class);

    	            int versionCode = context.getPackageManager().getPackageInfo("com.QuantumFinance.ui", 0).versionCode; 	            

    	            if(!cvb.isResult()){
    	            	mHandler.sendEmptyMessage(AppConstants.HANDLER_APK_STOP);
    	            }else if (cvb.getVersion() > versionCode) {
    	            	mHandler.sendMessage(mHandler.obtainMessage(AppConstants.HANDLER_VERSION_UPDATE,cvb));
    	            }
    			} catch (Exception e) {
    				mHandler.sendEmptyMessage(AppConstants.HANDLER_HTTPSTATUS_ERROR);
    			}
    			break;
    		default:
    			mHandler.sendEmptyMessage(AppConstants.HANDLER_HTTPSTATUS_ERROR);
    			break;
    		}
    	
    }

}
