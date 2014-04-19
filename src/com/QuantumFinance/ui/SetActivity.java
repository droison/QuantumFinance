package com.QuantumFinance.ui;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.QuantumFinance.BaiduMTJ.BaiduMTJActivity;
import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.db.AccountDAO;
import com.QuantumFinance.db.DbAccount;
import com.QuantumFinance.util.DialogUtil;
import com.QuantumFinance.util.StringUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SetActivity extends BaiduMTJActivity implements OnClickListener {

	private RelativeLayout set_headlayout, set_nicklayout, set_emaillayout, set_phonelayout, set_pwdlayout, set_snslayout;
	private ImageView set_headimg;
	private TextView set_nicktext, set_phonetext, set_emailtext, set_logout;
	private LinearLayout set_snsiconlayout, set_loginlayout;
	private DbAccount account;
	private AccountDAO accountDAO;
	private DialogUtil dialogUtil = new DialogUtil();
	private AlertDialog dlg;
	private final int PHOTO_REQUEST_CUT = 13;
	private final int PHOTO_REQUEST_TAKEPHOTO = 14;
	private final int PHOTO_REQUEST_GALLERY = 15;
	private File tempFile = new File(AppConstants.TEMP_HEAD_FILE_PATH);
	private final String TAG = "SetFragment";
	private ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private ImageLoadingListener displayListener = new DisplayListener();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		setUpView();
		setUpListener();
	}

	private void setUpView() {

		set_headlayout = (RelativeLayout) this.findViewById(R.id.set_headlayout);
		set_nicklayout = (RelativeLayout) this.findViewById(R.id.set_nicklayout);
		set_phonelayout = (RelativeLayout) this.findViewById(R.id.set_phonelayout);
		set_pwdlayout = (RelativeLayout) this.findViewById(R.id.set_pwdlayout);
		set_snslayout = (RelativeLayout) this.findViewById(R.id.set_snslayout);
		set_emaillayout = (RelativeLayout) this.findViewById(R.id.set_emaillayout);

		set_loginlayout = (LinearLayout) this.findViewById(R.id.set_loginlayout);

		set_snsiconlayout = (LinearLayout) this.findViewById(R.id.set_snsiconlayout);
		set_headimg = (ImageView) this.findViewById(R.id.set_headimg);
		set_nicktext = (TextView) this.findViewById(R.id.set_nicktext);
		set_phonetext = (TextView) this.findViewById(R.id.set_phonetext);
		set_emailtext = (TextView) this.findViewById(R.id.set_emailtext);

		set_logout = (TextView) this.findViewById(R.id.set_logout);

		accountDAO = new AccountDAO(this);
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.set_default).showImageForEmptyUri(R.drawable.set_default).showImageOnFail(R.drawable.set_default).cacheInMemory(true).cacheOnDisc(true).considerExifParams(true).displayer(new RoundedBitmapDisplayer(2)).build();
	}

	@Override
	public void onResume() {
		super.onResume();
		setUserInfo();
	}

	private void setUserInfo() {
		account = accountDAO.getAccount();
		if (account == null) {
			Intent toLogin = new Intent(SetActivity.this, LoginActivity.class);
			startActivity(toLogin);
			finish();
		} else {
			set_loginlayout.setVisibility(View.VISIBLE);

			set_snsiconlayout.removeAllViews();
			if (account.isBind_qq()) {
				ImageView qq = new ImageView(this);
				qq.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
				qq.setImageResource(R.drawable.set_qqicon);
				set_snsiconlayout.addView(qq);
			}
			if (account.isBind_weibo()) {
				ImageView weibo = new ImageView(this);
				weibo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
				weibo.setImageResource(R.drawable.set_sinaicon);
				set_snsiconlayout.addView(weibo);
			}

			imageLoader.displayImage(AppConstants.HTTPURL.serverIP + account.getFace(), set_headimg, options, displayListener);

			set_nicktext.setText(account.getUsername());
			set_phonetext.setText(account.getPhone());
			set_emailtext.setText(account.getEmail());
		}
	}

	private void setUpListener() {
		set_headlayout.setOnClickListener(this);
		set_nicklayout.setOnClickListener(this);
		set_phonelayout.setOnClickListener(this);
		set_pwdlayout.setOnClickListener(this);
		set_emaillayout.setOnClickListener(this);
		set_snslayout.setOnClickListener(this);
		set_logout.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.set_emaillayout:
			showInfoUpdateAlert(4);
			break;
		case R.id.set_headlayout:
			showImgDialog();
			break;
		case R.id.set_nicklayout:
			showInfoUpdateAlert(1);
			break;
		case R.id.set_phonelayout:
			if (account.getPhone() == null || account.getPhone().equals(""))
				showInfoUpdateAlert(2);
			break;
		case R.id.set_pwdlayout:
			showInfoUpdateAlert(3);
			break;
		case R.id.set_snslayout:
			if (!account.isBind_weibo() || !account.isBind_qq())
				showBindAlert(account);
			break;
		case R.id.set_logout:
			accountDAO.delete();
			Intent toLogin = new Intent(SetActivity.this, LoginActivity.class);
			startActivity(toLogin);
			finish();
			break;
		default:
			break;
		}
	}

	private Handler bindHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			dlg.cancel();
			dialogUtil.dismissDownloadDialog();
			switch (msg.what) {
			case AppConstants.HANDLER_MESSAGE_NORMAL:
				// 成功则保存并：setUserInfo();
				break;
			case AppConstants.HANDLER_MESSAGE_NONETWORK:
				dialogUtil.showNoNetWork(SetActivity.this);
				break;
			case AppConstants.HANDLER_HTTPSTATUS_ERROR:
				dialogUtil.showToast(SetActivity.this, "绑定失败，请稍后重试");
				break;
			}
		};
	};

	private Handler infoUpdateHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			dialogUtil.dismissDownloadDialog();
			switch (msg.what) {
			case AppConstants.HANDLER_MESSAGE_NORMAL:
				// 成功则保存并：setUserInfo();
				break;
			case AppConstants.HANDLER_MESSAGE_NONETWORK:
				dialogUtil.showNoNetWork(SetActivity.this);
				break;
			case AppConstants.HANDLER_HTTPSTATUS_ERROR:
				dialogUtil.showToast(SetActivity.this, "修改失败，请稍后重试");
				break;
			}
		};
	};

	private void showBindAlert(final DbAccount account) {
		dlg = new AlertDialog.Builder(this).create();
		dlg.show();
		Window window = dlg.getWindow();
		window.setContentView(R.layout.dialog_bindsns);

		TextView bind_weibo = (TextView) window.findViewById(R.id.bind_weibo);
		if (account.isBind_weibo()) {
			bind_weibo.setVisibility(View.GONE);
		} else {
			bind_weibo.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					try {
						Platform weibo = ShareSDK.getPlatform(SetActivity.this, SinaWeibo.NAME);
						weibo.SSOSetting(true);
						dialogUtil.showProgressDialog(SetActivity.this, "正在绑定");
						if (weibo.isValid()) {
							weibo.removeAccount();
						}

						weibo.setPlatformActionListener(new PlatformActionListener() {

							public void onError(Platform platform, int action, Throwable t) {
								dialogUtil.dismissDownloadDialog();
							}

							public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
								platform.getDb().get("weibo"); // 这是微博唯一ID
								// 此处运行绑定线程
							}

							public void onCancel(Platform platform, int action) {
								dialogUtil.dismissDownloadDialog();
							}

						});
						weibo.showUser(null);
					} catch (Exception e) {
						dialogUtil.showToast(SetActivity.this, "绑定异常，请重试");
						Log.e(TAG, "绑定微博异常", e);
					}
				}
			});
		}
		// 为确认按钮添加事件,执行退出应用操作
		TextView bind_qq = (TextView) window.findViewById(R.id.bind_qq);
		if (account.isBind_qq()) {
			bind_qq.setVisibility(View.GONE);
		} else {
			bind_qq.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					try {
						Platform qq = ShareSDK.getPlatform(SetActivity.this, QZone.NAME);
						qq.SSOSetting(true);
						dialogUtil.showProgressDialog(SetActivity.this, "正在绑定");
						if (qq.isValid()) {
							qq.removeAccount();
						}
						qq.setPlatformActionListener(new PlatformActionListener() {

							public void onError(Platform platform, int action, Throwable t) {
								dialogUtil.dismissDownloadDialog();
							}

							public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
								platform.getDb().get("weibo"); // 这是QQ唯一ID
								// 此处运行绑定线程
							}

							public void onCancel(Platform platform, int action) {
								dialogUtil.dismissDownloadDialog();
							}

						});
						qq.showUser(null);
					} catch (Exception e) {
						dialogUtil.showToast(SetActivity.this, "绑定异常，请重试");
						Log.e(TAG, "绑定QQ异常", e);
					}
				}
			});
		}
	}

	// 1是昵称，2是手机，3是密码
	private void showInfoUpdateAlert(final int type) {
		String text = "";
		switch (type) {
		case 1:
			text = "请输入新昵称";
			break;
		case 2:
			text = "请输入新手机号";
			break;
		case 3:
			text = "请输入新密码";
			break;
		case 4:
			text = "请输入新邮箱";
			break;
		}

		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.dialog_infoupdate, null);
		final EditText infoupdate_content = (EditText) textEntryView.findViewById(R.id.infoupdate_content);
		TextView infoupdate_prompt_text = (TextView) textEntryView.findViewById(R.id.infoupdate_prompt_text);
		infoupdate_content.setHint(text);
		infoupdate_prompt_text.setText(text);
		dlg = new AlertDialog.Builder(SetActivity.this).setView(textEntryView).setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String str = infoupdate_content.getText().toString();
				if (TextUtils.isEmpty(str)) {
					Toast.makeText(SetActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
				} else if (type == 2 && str.length() != 11) {
					Toast.makeText(SetActivity.this, "手机号输入不正确", Toast.LENGTH_SHORT).show();
				} else if (type == 3 && str.length() < 8) {
					Toast.makeText(SetActivity.this, "密码应当大于8位", Toast.LENGTH_SHORT).show();
				} else if (type == 4 && !StringUtil.checkEmail(str)) {
					Toast.makeText(SetActivity.this, "请输入正确的邮箱地址", Toast.LENGTH_SHORT).show();
				} else {
					dialogUtil.showProgressDialog(SetActivity.this, "正在更新");
					// 此处用来更新数据
				}

			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		}).create();
		dlg.show();

	}

	private void showImgDialog() {
		final String items[] = { "拍照", "相册选择" };
		new AlertDialog.Builder(this).setTitle("头像设置").setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch (which) {
				case 0:
					dialog.dismiss();
					// 调用系统的拍照功能
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					// 指定调用相机拍照后照片的储存路径
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
					startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
					break;
				case 1:
					dialog.dismiss();
					Intent i = new Intent(Intent.ACTION_PICK, null);
					i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
					startActivityForResult(i, PHOTO_REQUEST_GALLERY);
				}
			}
		}).show();

	}

	private void startPhotoZoom(Uri uri, int size) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:
			startPhotoZoom(Uri.fromFile(tempFile), 114);
			break;

		case PHOTO_REQUEST_GALLERY:
			if (data != null)
				startPhotoZoom(data.getData(), 114);
			break;

		case PHOTO_REQUEST_CUT:
			if (data != null) {
				Bitmap bm = data.getParcelableExtra("data");
				dialogUtil.showProgressDialog(SetActivity.this, "正在修改");
				// 处理图片上传
				break;
			}
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	class DisplayListener extends SimpleImageLoadingListener {

		final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(imageView.getHeight(), imageView.getHeight());
				lp.addRule(RelativeLayout.CENTER_IN_PARENT);
				imageView.setLayoutParams(lp);
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
