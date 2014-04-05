package com.QuantumFinance.util;

import com.QuantumFinance.ui.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class DialogUtil {

	private ProgressDialog progressDialog;
	private Dialog downloadDialog;

	public void showProgressDialog(Context mContext, CharSequence message) {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(mContext);
			progressDialog.setIndeterminate(true);
		}

		progressDialog.setMessage(message);
		progressDialog.show();
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_BACK:
					progressDialog.dismiss();
					return true;
				}
				return false;
			}
		});
	}

	public void dismissProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}


	public void dismissDownloadDialog() {
		if (downloadDialog != null) {
			downloadDialog.dismiss();
		}
	}
	
	public void showToast(Activity activity,String msg){
		Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
	}

	public void showNoNetWork(Activity activity) {
		View view = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.offline, (ViewGroup) activity.findViewById(R.id.toast_layout_root));
		Toast toast = new Toast(activity);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setView(view);
		toast.show();
	}

}
