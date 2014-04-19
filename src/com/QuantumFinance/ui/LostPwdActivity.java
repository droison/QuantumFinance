package com.QuantumFinance.ui;

import com.QuantumFinance.BaiduMTJ.BaiduMTJActivity;
import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.util.DialogUtil;
import com.QuantumFinance.util.StringUtil;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class LostPwdActivity extends BaiduMTJActivity implements OnClickListener {

	private EditText lostpwd_password, lostpwd_repassword, lostpwd_email;
	private TextView lostpwd_submit;
	private DialogUtil dialogUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lostpwd);
		initData();
		setUpView();

	}

	// 初始化view并初始化fragment
	private void setUpView() {
		lostpwd_password = (EditText) this.findViewById(R.id.lostpwd_password);
		lostpwd_repassword = (EditText) this.findViewById(R.id.lostpwd_repassword);
		lostpwd_email = (EditText) this.findViewById(R.id.lostpwd_email);
		lostpwd_submit = (TextView) this.findViewById(R.id.lostpwd_submit);
		lostpwd_submit.setOnClickListener(this);

		dialogUtil = new DialogUtil();
	}

	private void initData() {

	}

	//修改密码用的handler
	private Handler submiltHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			dialogUtil.dismissDownloadDialog();
			switch (msg.what) {
			case AppConstants.HANDLER_MESSAGE_NORMAL:
				break;
			case AppConstants.HANDLER_MESSAGE_NONETWORK:
				dialogUtil.showNoNetWork(LostPwdActivity.this);
				break;
			case AppConstants.HANDLER_HTTPSTATUS_ERROR:
				dialogUtil.showToast(LostPwdActivity.this, "修改失败，请稍后重试");
				break;
			}

		};
	};

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.lostpwd_submit:
			String email = lostpwd_email.getText().toString();
			String password = lostpwd_password.getText().toString();
			String rePassword = lostpwd_repassword.getText().toString();
			if (TextUtils.isEmpty(password)) {
				dialogUtil.showToast(this, "密码不能为空");
			} else if (TextUtils.isEmpty(rePassword)) {
				dialogUtil.showToast(this, "请在输入一次密码");
			} else if (TextUtils.isEmpty(email)) {
				dialogUtil.showToast(this, "邮箱不能为空");
			} else if (password.length() < 8) {
				dialogUtil.showToast(this, "密码长度要大于8位");
			} else if (!password.equals(rePassword)) {
				dialogUtil.showToast(this, "两次密码输入不一致");
			} else if (!StringUtil.checkEmail(email)) {
				dialogUtil.showToast(this, "邮箱格式错误");
			} else {
				dialogUtil.showProgressDialog(this, "正在提交");
				// 运行修改密码线程
			}
			break;
		default:
			break;
		}
	}

}
