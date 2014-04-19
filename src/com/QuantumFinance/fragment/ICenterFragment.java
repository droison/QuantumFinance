package com.QuantumFinance.fragment;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.QuantumFinance.BaiduMTJ.BaiduMTJFragment;
import com.QuantumFinance.ui.AboutActivity;
import com.QuantumFinance.ui.EvaluateActivity;
import com.QuantumFinance.ui.ICollectActivity;
import com.QuantumFinance.ui.MainActivity;
import com.QuantumFinance.ui.R;
import com.QuantumFinance.ui.SetActivity;
import com.QuantumFinance.util.DialogUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ICenterFragment extends BaiduMTJFragment {

	private LayoutInflater layoutInflater;
	private MainActivity parentActivity;
	private View root;

	private DialogUtil dialogUtil;
	private LinearLayout icenter_layout;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private ImageLoadingListener displayListener = new DisplayListener();

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
		item_icenter_logo1.setImageResource(R.drawable.icenter1);

		View v2 = layoutInflater.inflate(R.layout.item_icenter, null);
		ImageView item_icenter_logo2 = (ImageView) v2.findViewById(R.id.item_icenter_logo);
		TextView item_icenter_title2 = (TextView) v2.findViewById(R.id.item_icenter_title);
		item_icenter_logo2.setImageResource(R.drawable.icenter2);
		item_icenter_title2.setText("理财评估");

		View v3 = layoutInflater.inflate(R.layout.item_icenter, null);
		ImageView item_icenter_logo3 = (ImageView) v3.findViewById(R.id.item_icenter_logo);
		TextView item_icenter_title3 = (TextView) v3.findViewById(R.id.item_icenter_title);
		item_icenter_logo3.setImageResource(R.drawable.icenter3);
		item_icenter_title3.setText("我的收藏");

		View v4 = layoutInflater.inflate(R.layout.item_icenter, null);
		ImageView item_icenter_logo4 = (ImageView) v4.findViewById(R.id.item_icenter_logo);
		TextView item_icenter_title4 = (TextView) v4.findViewById(R.id.item_icenter_title);
		item_icenter_logo4.setImageResource(R.drawable.icenter4);
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
				Intent toEva = new Intent(parentActivity, EvaluateActivity.class);
				parentActivity.startActivity(toEva);
			}
		});

		v3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent toICollect = new Intent(parentActivity, ICollectActivity.class);
				parentActivity.startActivity(toICollect);
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
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	class DisplayListener extends SimpleImageLoadingListener {

		final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

}