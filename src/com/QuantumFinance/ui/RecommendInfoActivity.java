package com.QuantumFinance.ui;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.QuantumFinance.BaiduMTJ.BaiduMTJActivity;
import com.QuantumFinance.Thread.ThreadExecutor;
import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.net.GetData;
import com.QuantumFinance.net.base.PPTBase;
import com.QuantumFinance.net.base.RecommendBase;
import com.QuantumFinance.net.base.RecommendInfoBase;
import com.QuantumFinance.util.DialogUtil;
import com.QuantumFinance.util.DpSpDip2Px;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class RecommendInfoActivity extends BaiduMTJActivity {

	private TextView recommendinfo_deadline;
	private WebView recommendinfo_description;
	private TextView recommendinfo_eair;
	private ImageView recommendinfo_logo;
	private ProgressBar recommendinfo_progressbar;
	private TextView recommendinfo_schedule;
	private TextView recommendinfo_show_url;
	private TextView recommendinfo_title;
	private TextView recommendinfo_total_money;

	private RecommendBase rb = new RecommendBase();
	private RecommendInfoBase rib = new RecommendInfoBase();
	private DialogUtil dialogUtil = new DialogUtil();
	private boolean isPPT = false;
	private int recommendId = 0;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private ImageLoadingListener displayListener = new DisplayListener();
	private DpSpDip2Px dp2sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommendinfo);
		setUpView();
		initData();
		setUpListener();
	}

	private void initData() {

		isPPT = getIntent().getBooleanExtra("isPPT", false);
		if (isPPT) {
			PPTBase pb = (PPTBase) getIntent().getSerializableExtra("PPTBase");
			recommendId = pb.getComment_or_product_id();
		} else {
			rb = (RecommendBase) getIntent().getSerializableExtra("rb");

			recommendinfo_deadline.setText(rb.getDeadline() + "个月");
			recommendinfo_eair.setText(rb.getEair());
			recommendinfo_title.setText(rb.getTitle());
			if (rb.getSchedule().equals("无")) {
				recommendinfo_progressbar.setProgress(0);
				recommendinfo_schedule.setText(rb.getSchedule());
			} else {
				float pro = Float.valueOf(rb.getSchedule().replace("%", ""));
				recommendinfo_progressbar.setProgress((int) pro);
				recommendinfo_schedule.setText((int) pro + "%");
			}
			recommendId = rb.getId();
			imageLoader.displayImage(AppConstants.HTTPURL.serverIP + rb.getLogo(), recommendinfo_logo, options, displayListener);
		}

		dialogUtil.showProgressDialog(this, "正在读取数据...");
		ThreadExecutor.execute(new GetData(this, mHandler, AppConstants.HTTPURL.recommendInfo + recommendId, 5));

	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			dialogUtil.dismissProgressDialog();
			switch (msg.what) {
			case AppConstants.HANDLER_MESSAGE_NORMAL:
				rib = (RecommendInfoBase) msg.obj;
				if (rib == null)
					return;
				recommendinfo_deadline.setText(rib.getDeadline() + "个月");
				recommendinfo_eair.setText(rib.getEair());
				recommendinfo_schedule.setText(rib.getSchedule());
				recommendinfo_title.setText(rib.getTitle());
				if (rib.getSchedule().equals("无")) {
					recommendinfo_progressbar.setProgress(0);
					recommendinfo_schedule.setText(rib.getSchedule());
				} else {
					float pro = Float.valueOf(rib.getSchedule().replace("%", ""));
					recommendinfo_progressbar.setProgress((int) pro);
					recommendinfo_schedule.setText((int) pro + "%");
				}
				recommendinfo_total_money.setText("￥" + rib.getTotal_money());
				recommendinfo_description.loadDataWithBaseURL(null, new StringBuilder(AppConstants.FONT_START).append(AppConstants.BODY_START).append(rib.getDescription()).append(AppConstants.BODY_END).toString(), AppConstants.TEXT_HTML, AppConstants.UTF8, null);
				recommendinfo_description.setBackgroundColor(getResources().getColor(R.color.recommendinfo_item_bg1));
				imageLoader.displayImage(AppConstants.HTTPURL.serverIP + rib.getLogo(), recommendinfo_logo, options, displayListener);
				break;
			case AppConstants.HANDLER_HTTPSTATUS_ERROR:
				Toast.makeText(RecommendInfoActivity.this, "网络访问出错", Toast.LENGTH_SHORT).show();
				break;
			case AppConstants.HANDLER_MESSAGE_NONETWORK:
				dialogUtil.showNoNetWork(RecommendInfoActivity.this);
				break;
			}
		};
	};

	private void setUpListener() {
		recommendinfo_show_url.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (rib.getShow_url() != null && !rib.getShow_url().equals("")) {
					Intent intent = new Intent();
					intent.setAction("android.intent.action.VIEW");
					Uri content_url = Uri.parse(rib.getShow_url());
					intent.setData(content_url);
					startActivity(intent);
				}

			}
		});
	}

	private void launchOtherApp(String packageName) throws NameNotFoundException {
		PackageManager pm = getPackageManager();
		PackageInfo pi = pm.getPackageInfo(packageName, 0);

		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(pi.packageName);
		
		List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);

		ResolveInfo ri = apps.iterator().next();
		if (ri != null) {
			packageName = ri.activityInfo.packageName;
			String className = ri.activityInfo.name;
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			ComponentName cn = new ComponentName(packageName, className);
			intent.setComponent(cn);
			startActivity(intent);
		}
	}

	private void setUpView() {
		recommendinfo_deadline = (TextView) this.findViewById(R.id.recommendinfo_deadline);

		recommendinfo_description = (WebView) this.findViewById(R.id.recommendinfo_description);
		recommendinfo_eair = (TextView) this.findViewById(R.id.recommendinfo_eair);
		recommendinfo_total_money = (TextView) this.findViewById(R.id.recommendinfo_total_money);
		recommendinfo_title = (TextView) this.findViewById(R.id.recommendinfo_title);
		recommendinfo_show_url = (TextView) this.findViewById(R.id.recommendinfo_show_url);
		recommendinfo_schedule = (TextView) this.findViewById(R.id.recommendinfo_schedule);
		recommendinfo_progressbar = (ProgressBar) this.findViewById(R.id.recommendinfo_progressbar);
		recommendinfo_logo = (ImageView) this.findViewById(R.id.recommendinfo_logo);

		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.img_default).showImageForEmptyUri(R.drawable.img_default).showImageOnFail(R.drawable.img_default).considerExifParams(true).displayer(new RoundedBitmapDisplayer(1)).build();
		dp2sp = new DpSpDip2Px(this);
	}

	class DisplayListener extends SimpleImageLoadingListener {

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			ImageView imageView = (ImageView) view;
			int h = dp2sp.dip2px(35);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(h, h);
			lp.setMargins(dp2sp.dip2px(3), dp2sp.dip2px(3), 0, 0);
			imageView.setLayoutParams(lp);
			if (loadedImage != null) {
				imageView.setImageBitmap(loadedImage);
			}
		}
	}

}
