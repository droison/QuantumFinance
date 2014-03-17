package com.QuantumFinance.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.QuantumFinance.BaiduMTJ.BaiduMTJFragment;
import com.QuantumFinance.Thread.ThreadExecutor;
import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.net.AsyncImageLoader;
import com.QuantumFinance.net.AsyncImageLoader.ImageCallback;
import com.QuantumFinance.net.GetData;
import com.QuantumFinance.net.base.RecommendBase;
import com.QuantumFinance.ui.AboutActivity;
import com.QuantumFinance.ui.MainActivity;
import com.QuantumFinance.ui.R;
import com.QuantumFinance.ui.SetActivity;
import com.QuantumFinance.ui.adapter.RecommendAdapter;
import com.QuantumFinance.util.DialogUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ICenterFragment extends BaiduMTJFragment {

	private LayoutInflater layoutInflater;
	private MainActivity parentActivity;
	private View root;

	private DialogUtil dialogUtil;
	private LinearLayout icenter_layout;
	private AsyncImageLoader asynImageLoader;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_icenter, null);
		setUpView();
		initData();
		return root;
	}

	private void initData() {
		View v1 = layoutInflater.inflate(R.layout.item_icenter, null);
		ImageView item_icenter_logo1 = (ImageView) v1.findViewById(R.id.item_icenter_logo);
		TextView item_icenter_title1 = (TextView) v1.findViewById(R.id.item_icenter_title);
		item_icenter_title1.setText("我的账号");

		View v2 = layoutInflater.inflate(R.layout.item_icenter, null);
		ImageView item_icenter_logo2 = (ImageView) v2.findViewById(R.id.item_icenter_logo);
		TextView item_icenter_title2 = (TextView) v2.findViewById(R.id.item_icenter_title);
		item_icenter_title2.setText("理财评估");

		View v3 = layoutInflater.inflate(R.layout.item_icenter, null);
		ImageView item_icenter_logo3 = (ImageView) v3.findViewById(R.id.item_icenter_logo);
		TextView item_icenter_title3 = (TextView) v3.findViewById(R.id.item_icenter_title);
		item_icenter_title3.setText("我的收藏");

		View v4 = layoutInflater.inflate(R.layout.item_icenter, null);
		ImageView item_icenter_logo4 = (ImageView) v4.findViewById(R.id.item_icenter_logo);
		TextView item_icenter_title4 = (TextView) v4.findViewById(R.id.item_icenter_title);
		item_icenter_title4.setText("关于我们");

		v1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent toSet = new Intent(parentActivity, SetActivity.class);
				parentActivity.startActivity(toSet);
			}
		});

		v2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});

		v3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

			}
		});

		v4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent toAbout = new Intent(parentActivity, AboutActivity.class);
				parentActivity.startActivity(toAbout);
			}
		});

		icenter_layout.addView(v1);
		icenter_layout.addView(v2);
		icenter_layout.addView(v3);
		icenter_layout.addView(v4);
	}

	public void setUpView() {
		parentActivity = (MainActivity) getActivity();

		if (parentActivity == null)
			return;

		layoutInflater = LayoutInflater.from(parentActivity);

		icenter_layout = (LinearLayout) root.findViewById(R.id.icenter_layout);
		dialogUtil = new DialogUtil();
		asynImageLoader = new AsyncImageLoader();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

}