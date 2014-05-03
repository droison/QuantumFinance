package com.QuantumFinance.ui.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.net.base.PaperBase;
import com.QuantumFinance.ui.PaperInfoActivity;
import com.QuantumFinance.ui.R;
import com.QuantumFinance.util.DpSpDip2Px;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class PaperAdapter extends BaseAdapter {
	private List<PaperBase> pbs;
	private Context mContext;
	private LayoutInflater layoutInflater;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private ImageLoadingListener displayListener = new DisplayListener();
	private DpSpDip2Px dp2px;
	private Handler mHandler;

	public PaperAdapter(List<PaperBase> pbs, Context mContext, Handler mHandler) {
		this.pbs = pbs;
		this.mContext = mContext;
		this.layoutInflater = LayoutInflater.from(mContext);
		dp2px = new DpSpDip2Px(mContext);
		this.mHandler = mHandler;
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.img_default).showImageForEmptyUri(R.drawable.img_default).showImageOnFail(R.drawable.img_default).cacheInMemory(false).cacheOnDisc(true).build();
	}

	@Override
	public int getCount() {
		return pbs.size();
	}

	@Override
	public Object getItem(int arg0) {
		return pbs.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View converView, ViewGroup arg2) {
		final PaperBase pb = pbs.get(arg0);
		converView = layoutInflater.inflate(R.layout.item_paper, null);
		ImageView item_paper_logo = (ImageView) converView.findViewById(R.id.item_paper_logo);
		TextView item_paper_title = (TextView) converView.findViewById(R.id.item_paper_title);
		TextView item_paper_comment = (TextView) converView.findViewById(R.id.item_paper_comment);
		TextView item_paper_view = (TextView) converView.findViewById(R.id.item_paper_view);
		TextView item_paper_content = (TextView) converView.findViewById(R.id.item_paper_content);

		item_paper_comment.setText("评论:" + pb.getComments());
		item_paper_view.setText("浏览:" + pb.getView_count());
		item_paper_title.setText(pb.getTitle());

		item_paper_content.setText(Html.fromHtml(new StringBuilder("<html><head></head><body>").append(pb.getSynopsis()).append("</body>").toString()));
		imageLoader.displayImage(AppConstants.HTTPURL.serverIP + pb.getLogo(), item_paper_logo, options, displayListener);

		OnClickListener click = new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent toPaperInfo = new Intent(mContext, PaperInfoActivity.class);
				toPaperInfo.putExtra("pb", pb);
				mContext.startActivity(toPaperInfo);
			}
		};
		converView.setOnClickListener(click);
		converView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				if (mHandler != null) {
					mHandler.sendEmptyMessage(pb.getId());
				}
				return true;
			}
		});
		return converView;
	}

	class DisplayListener extends SimpleImageLoadingListener {

		final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				int h = dp2px.dip2px(98);
				int w = h * loadedImage.getWidth() / loadedImage.getHeight();
				FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(w, h);
				// imageView.setLayoutParams(lp);
				imageView.setScaleType(ScaleType.CENTER_CROP);
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			} else {
				ImageView imageView = (ImageView) view;
				int h = imageView.getHeight();
				FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(h, h);
				lp.setMargins(dp2px.dip2px(10), dp2px.dip2px(10), 0, dp2px.dip2px(10));
				imageView.setLayoutParams(lp);
				imageView.setImageResource(R.drawable.img_default);
			}
		}
	}
}
