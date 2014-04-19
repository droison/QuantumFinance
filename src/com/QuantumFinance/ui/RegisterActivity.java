package com.QuantumFinance.ui;

import com.QuantumFinance.BaiduMTJ.BaiduMTJActivity;
import com.QuantumFinance.Thread.ThreadExecutor;
import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.db.AccountDAO;
import com.QuantumFinance.db.DbAccount;
import com.QuantumFinance.net.PostData;
import com.QuantumFinance.net.base.RegBase;
import com.QuantumFinance.net.base.RegResult;
import com.QuantumFinance.util.DialogUtil;
import com.QuantumFinance.util.StringUtil;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends BaiduMTJActivity implements OnClickListener {

	private EditText reg_username, reg_password, reg_email, reg_repassword;
	private TextView reg_reg, reg_login;
	private DialogUtil dialogUtil;
	private AccountDAO accountDAO;
	private DbAccount dbAccount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initData();
		setUpView();
	}

	private void setUpView() {
		reg_username = (EditText) this.findViewById(R.id.reg_username);
		reg_password = (EditText) this.findViewById(R.id.reg_password);
		reg_repassword = (EditText) this.findViewById(R.id.reg_repassword);
		reg_email = (EditText) this.findViewById(R.id.reg_email);

		reg_reg = (TextView) this.findViewById(R.id.reg_reg);
		reg_login = (TextView) this.findViewById(R.id.reg_login);
		reg_reg.setOnClickListener(this);
		reg_login.setOnClickListener(this);

		dialogUtil = new DialogUtil();
		accountDAO = new AccountDAO(this);
		dbAccount = new DbAccount();
	}

	private void initData() {

	}

	private Handler regHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			dialogUtil.dismissProgressDialog();
			switch (msg.what) {
			case AppConstants.HANDLER_MESSAGE_NORMAL:
				RegResult reg = (RegResult) msg.obj;
				if (reg.getError() != null) {
					dialogUtil.showToast(RegisterActivity.this, "邮箱已存在");
				} else if (reg.getId() == 0) {
					dialogUtil.showToast(RegisterActivity.this, "注册失败，请稍后重试");
				} else {
					dbAccount.setToken(reg.getAuthentication_token());
					dbAccount.setUserid(reg.getId());
					dbAccount.setSex(reg.getSex());
					accountDAO.save(dbAccount);
					setResult(RESULT_OK);
					dialogUtil.showToast(RegisterActivity.this, "注册成功");
					finish();
				}
				break;
			case AppConstants.HANDLER_MESSAGE_NONETWORK:
				dialogUtil.showNoNetWork(RegisterActivity.this);
				break;
			case AppConstants.HANDLER_MESSAGE_NULL:
				dialogUtil.showToast(RegisterActivity.this, "注册成功");
				finish();
				break;
			case AppConstants.HANDLER_HTTPSTATUS_ERROR:
				dialogUtil.showToast(RegisterActivity.this, "注册失败，请稍后重试");
				break;
			}

		};
	};

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.reg_login:
			setResult(RESULT_CANCELED);
			finish();
			break;
		case R.id.reg_reg:
			String username = reg_username.getText().toString();
			String password = reg_password.getText().toString();
			String rePassword = reg_repassword.getText().toString();
			String email = reg_email.getText().toString();
			if (TextUtils.isEmpty(username)) {
				dialogUtil.showToast(this, "用户名不能为空");
			} else if (TextUtils.isEmpty(password)) {
				dialogUtil.showToast(this, "密码不能为空");
			} else if (TextUtils.isEmpty(rePassword)) {
				dialogUtil.showToast(this, "请在输入一次密码");
			} else if (password.length() < 8) {
				dialogUtil.showToast(this, "密码长度要大于8位");
			} else if (!password.equals(rePassword)) {
				dialogUtil.showToast(this, "两次密码输入不一致");
			} else if (TextUtils.isEmpty(email)) {
				dialogUtil.showToast(this, "请输入邮箱");
			} else if (!StringUtil.checkEmail(email)) {
				dialogUtil.showToast(this, "请正确输入邮箱地址");
			} else {
				dialogUtil.showProgressDialog(this, "正在注册");
				dbAccount.setUsername(username);
				dbAccount.setEmail(email);
				// 开始注册线程
				RegBase rb = new RegBase(username, email, password, rePassword);
				ThreadExecutor.execute(new PostData(RegisterActivity.this, regHandler, rb, 2));
			}
			break;
		default:
			break;
		}
	}

}
