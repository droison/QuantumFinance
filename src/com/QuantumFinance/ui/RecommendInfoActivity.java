package com.QuantumFinance.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
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

public class RecommendInfoActivity extends BaiduMTJActivity {

	private TextView recommendinfo_deadline;
	private TextView recommendinfo_description;
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
		if(isPPT){
			PPTBase pb = (PPTBase) getIntent().getSerializableExtra("PPTBase");
			recommendId = pb.getComment_or_product_id();
		}else{
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
				if(rib==null)
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
				recommendinfo_description.setText(rib.getDescription());
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

	private void setUpView() {
		recommendinfo_deadline = (TextView) this.findViewById(R.id.recommendinfo_deadline);

		recommendinfo_description = (TextView) this.findViewById(R.id.recommendinfo_description);
		recommendinfo_eair = (TextView) this.findViewById(R.id.recommendinfo_eair);
		recommendinfo_total_money = (TextView) this.findViewById(R.id.recommendinfo_total_money);
		recommendinfo_title = (TextView) this.findViewById(R.id.recommendinfo_title);
		recommendinfo_show_url = (TextView) this.findViewById(R.id.recommendinfo_show_url);
		recommendinfo_schedule = (TextView) this.findViewById(R.id.recommendinfo_schedule);
		recommendinfo_progressbar = (ProgressBar) this.findViewById(R.id.recommendinfo_progressbar);
		recommendinfo_logo = (ImageView) this.findViewById(R.id.recommendinfo_logo);

	}
}
