package com.QuantumFinance.ui;

import com.QuantumFinance.BaiduMTJ.BaiduMTJActivity;
import com.QuantumFinance.Thread.ThreadExecutor;
import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.net.PostData;
import com.QuantumFinance.net.base.PostChangePwd;
import com.QuantumFinance.net.base.Result;
import com.QuantumFinance.util.DialogUtil;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePwdActivity extends BaiduMTJActivity implements OnClickListener {

	private EditText changepwd_text1, changepwd_text2, changepwd_text3;
	private TextView changepwd_submit;
	private DialogUtil dialogUtil;
	private String token;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changepwd);
		initData();
		setUpView();

	}

	// 初始化view并初始化fragment
	private void setUpView() {
		changepwd_text1 = (EditText) this.findViewById(R.id.changepwd_text1);
		changepwd_text2 = (EditText) this.findViewById(R.id.changepwd_text2);
		changepwd_text3 = (EditText) this.findViewById(R.id.changepwd_text3);
		changepwd_submit = (TextView) this.findViewById(R.id.changepwd_submit);
		changepwd_submit.setOnClickListener(this);

		dialogUtil = new DialogUtil();
	}

	private void initData() {
		token = this.getIntent().getStringExtra("token");
	}

	// 修改密码用的handler
	private Handler submiltHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			dialogUtil.dismissProgressDialog();
			switch (msg.what) {
			case AppConstants.HANDLER_MESSAGE_NORMAL:
				Result result = (Result) msg.obj;
				if (result.isStatus()) {
					dialogUtil.showToast(ChangePwdActivity.this, "修改成功！");
					finish();
				} else {
					dialogUtil.showToast(ChangePwdActivity.this, "失败:" + result.getMessage());
				}
				break;
			case AppConstants.HANDLER_MESSAGE_NONETWORK:
				dialogUtil.showNoNetWork(ChangePwdActivity.this);
				break;
			case AppConstants.HANDLER_HTTPSTATUS_ERROR:
				dialogUtil.showToast(ChangePwdActivity.this, "修改失败，请稍后重试");
				break;
			}

		};
	};

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.changepwd_submit:
			String oldpwd = changepwd_text1.getText().toString();
			String newpwd = changepwd_text2.getText().toString();
			String repwd = changepwd_text3.getText().toString();
			if (TextUtils.isEmpty(newpwd)) {
				dialogUtil.showToast(this, "密码不能为空");
			} else if (TextUtils.isEmpty(repwd)) {
				dialogUtil.showToast(this, "请在输入一次密码");
			} else if (newpwd.length() < 8) {
				dialogUtil.showToast(this, "密码长度至少8位");
			} else if (!newpwd.equals(repwd)) {
				dialogUtil.showToast(this, "两次密码输入不一致");
			} else {
				dialogUtil.showProgressDialog(this, "正在提交");
				// 运行修改密码线程
				PostChangePwd pcp = new PostChangePwd();
				pcp.setUser(oldpwd, newpwd, repwd);
				pcp.setToken(token);
				ThreadExecutor.execute(new PostData(ChangePwdActivity.this, submiltHandler, pcp, 8));
			}
			break;
		default:
			break;
		}
	}

}
