package com.QuantumFinance.ui;

import java.util.HashMap;

import com.QuantumFinance.BaiduMTJ.BaiduMTJActivity;
import com.QuantumFinance.Thread.ThreadExecutor;
import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.db.AccountDAO;
import com.QuantumFinance.net.PostData;
import com.QuantumFinance.net.base.LoginBase;
import com.QuantumFinance.net.base.LoginOrRegResult;
import com.QuantumFinance.util.DialogUtil;
import com.QuantumFinance.util.StringUtil;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends BaiduMTJActivity implements OnClickListener {

	private EditText login_email, login_password;
	private TextView login_lostpass, login_login, login_register;
	private ImageView  login_weibo, login_qq;
	private DialogUtil dialogUtil;
	private AccountDAO accountDAO;
	private static final int REGISTERCODE = 11;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ShareSDK.initSDK(this);
		initData();
		setUpView();

	}

	private void setUpView() {
		login_email = (EditText) this.findViewById(R.id.login_email);
		login_password = (EditText) this.findViewById(R.id.login_password);
		login_lostpass = (TextView) this.findViewById(R.id.login_lostpass);
		login_login = (TextView) this.findViewById(R.id.login_login);
		login_qq = (ImageView) this.findViewById(R.id.login_qq);
		login_register = (TextView) this.findViewById(R.id.login_register);
		login_weibo = (ImageView) this.findViewById(R.id.login_weibo);

		login_lostpass.setOnClickListener(this);
		login_login.setOnClickListener(this);
		login_register.setOnClickListener(this);
		login_weibo.setOnClickListener(this);
		login_qq.setOnClickListener(this);
		dialogUtil = new DialogUtil();
		accountDAO = new AccountDAO(this);
	}

	private void initData() {
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.login_register:
			Intent toRegister = new Intent(this, RegisterActivity.class);
			startActivityForResult(toRegister, REGISTERCODE);
			break;

		case R.id.login_weibo:
			Platform weibo = ShareSDK.getPlatform(this, SinaWeibo.NAME);
			if(weibo.isValid()){
				weibo.removeAccount();
			}
			dialogUtil.showProgressDialog(LoginActivity.this, "正在登录");
			weibo.SSOSetting(true);
			weibo.setPlatformActionListener(new PlatformActionListener() {

				public void onError(Platform platform, int action, Throwable t) {
					// 操作失败的处理代码
					dialogUtil.dismissDownloadDialog();
				}

				public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
					String weiboId = platform.getDb().get("weibo");
					String imageUrl = (String) res.get("profile_image_url");
					String nickname = (String) res.get("screen_name");
					

					//运行社交帐号注册登录线程
				}

				public void onCancel(Platform platform, int action) {
					dialogUtil.dismissDownloadDialog();
					// 操作取消的处理代码
				}

			});
			weibo.showUser(null);
			break;
		case R.id.login_qq:
			Platform qq = ShareSDK.getPlatform(this, QZone.NAME);
			if(qq.isValid())
			{
				qq.removeAccount();
			}
			dialogUtil.showProgressDialog(LoginActivity.this, "正在登录");
			qq.SSOSetting(true);
			qq.setPlatformActionListener(new PlatformActionListener() {

				public void onError(Platform platform, int action, Throwable t) {
					// 操作失败的处理代码
					dialogUtil.dismissDownloadDialog();
				}

				public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
					
					String weiboId = platform.getDb().get("weibo");
					String nickname = (String) res.get("nickname");
					String imageUrl = (String) res.get("figureurl_2");
					

					//运行社交帐号注册登录线程
				}

				public void onCancel(Platform platform, int action) {
					// 操作取消的处理代码
					dialogUtil.dismissDownloadDialog();
				}

			});
			qq.showUser(null);
			break;
		case R.id.login_login:
			String email = login_email.getText().toString();
			String password = login_password.getText().toString();
			if (TextUtils.isEmpty(email)) {
				dialogUtil.showToast(this, "邮箱不能为空");
			} else if (TextUtils.isEmpty(password)) {
				dialogUtil.showToast(this, "密码不能为空");
			} else if (!StringUtil.checkEmail(email)) {
				dialogUtil.showToast(this, "邮箱格式错误");
			} else {
				dialogUtil.showProgressDialog(this, "正在登录");

				LoginBase lb = new LoginBase(email, password);
				ThreadExecutor.execute(new PostData(LoginActivity.this, loginHandler, lb, 3));
			}
			break;
		case R.id.login_lostpass:
			Intent toLostPwd = new Intent(LoginActivity.this, LostPwdActivity.class);
			startActivity(toLostPwd);
			break;
		}
	}
	
	private Handler loginHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			dialogUtil.dismissProgressDialog();
			switch (msg.what) {
			case AppConstants.HANDLER_MESSAGE_NORMAL:
				LoginOrRegResult.Login loginResult= (LoginOrRegResult.Login)msg.obj;
				if(loginResult.isResult()){
					//此处要记得保存数据
					setResult(RESULT_OK);
					finish();
				}else{
					dialogUtil.showToast(LoginActivity.this, loginResult.getMessage());
				}
				break;
			case AppConstants.HANDLER_MESSAGE_NONETWORK:
				dialogUtil.showNoNetWork(LoginActivity.this);
				break;
			case AppConstants.HANDLER_HTTPSTATUS_ERROR:
				dialogUtil.showToast(LoginActivity.this, "登录失败，请稍后重试");
				break;
			}

		};
	};

	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ShareSDK.stopSDK(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_CANCELED)
			return;
		else if(resultCode == RESULT_OK){
			setResult(RESULT_OK);
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
