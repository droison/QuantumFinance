package com.QuantumFinance.ui.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.net.base.RecommendBase;
import com.QuantumFinance.ui.R;
import com.QuantumFinance.ui.RecommendInfoActivity;
import com.QuantumFinance.util.DpSpDip2Px;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RecommendAdapter extends BaseAdapter {
	private List<RecommendBase> rbs;
	private Context mContext;
	private LayoutInflater layoutInflater;
	private Typeface typeFace;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private ImageLoadingListener displayListener = new DisplayListener();
	private DpSpDip2Px dp2px;

	public RecommendAdapter(List<RecommendBase> rbs, Context mContext) {
		this.rbs = rbs;
		this.mContext = mContext;
		this.layoutInflater = LayoutInflater.from(mContext);
		typeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/ios7.ttf");
		dp2px = new DpSpDip2Px(mContext);
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.img_default).showImageForEmptyUri(R.drawable.img_default).showImageOnFail(R.drawable.img_default).cacheInMemory(false).cacheOnDisc(true).build();
	}

	@Override
	public int getCount() {
		return rbs.size();
	}

	@Override
	public Object getItem(int arg0) {
		return rbs.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View converView, ViewGroup arg2) {
		final RecommendBase rb = rbs.get(arg0);
		converView = layoutInflater.inflate(R.layout.item_recommend, null);
		ImageView item_recommend_logo = (ImageView) converView.findViewById(R.id.item_recommend_logo);
		TextView item_recommend_deadline = (TextView) converView.findViewById(R.id.item_recommend_deadline);
		TextView item_recommend_eair = (TextView) converView.findViewById(R.id.item_recommend_eair);
		TextView item_recommend_schedule = (TextView) converView.findViewById(R.id.item_recommend_schedule);
		TextView item_recommend_title = (TextView) converView.findViewById(R.id.item_recommend_title);
		ProgressBar item_recommend_progressbar = (ProgressBar) converView.findViewById(R.id.item_recommend_progressbar);
		item_recommend_deadline.setText(rb.getDeadline());
		item_recommend_eair.setText(rb.getEair());
		item_recommend_eair.setTypeface(typeFace);
		imageLoader.displayImage(AppConstants.HTTPURL.serverIP + rb.getLogo(), item_recommend_logo, options, displayListener);
		item_recommend_title.setText(rb.getTitle());
		if (rb.getSchedule().equals("无")) {
			item_recommend_schedule.setText("无");
			item_recommend_progressbar.setProgress(0);
		} else {
			int schedule = Float.valueOf(rb.getSchedule().replace("%", "")).intValue();
			item_recommend_schedule.setText(schedule + "%");
			item_recommend_schedule.setTypeface(typeFace);
			item_recommend_progressbar.setProgress(schedule);
		}

		converView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent toRecommendInfo = new Intent(mContext, RecommendInfoActivity.class);
				toRecommendInfo.putExtra("rb", rb);
				mContext.startActivity(toRecommendInfo);
			}
		});
		return converView;
	}

	class DisplayListener extends SimpleImageLoadingListener {

		final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			ImageView imageView = (ImageView) view;
			int h = imageView.getHeight();
			imageView.setLayoutParams(new LinearLayout.LayoutParams(h,h));
			if (loadedImage != null) {
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

}
