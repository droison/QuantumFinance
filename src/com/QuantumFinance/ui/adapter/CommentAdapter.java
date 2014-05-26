package com.QuantumFinance.ui.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.net.base.CommentBase;
import com.QuantumFinance.ui.CommentActivity;
import com.QuantumFinance.ui.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {
	List<CommentBase> cbs;
	private Activity mActivity;
	private LayoutInflater layoutInflater;
	private ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private ImageLoadingListener displayListener = new DisplayListener();
	private int paper_id;

	public CommentAdapter(List<CommentBase> cbs, int paper_id, Activity mActivity) {
		this.cbs = cbs;
		this.mActivity = mActivity;
		this.layoutInflater = LayoutInflater.from(mActivity);
		this.paper_id = paper_id;
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.head_default).showImageForEmptyUri(R.drawable.head_default).showImageOnFail(R.drawable.head_default).cacheInMemory(true).cacheOnDisc(true).considerExifParams(true).displayer(new RoundedBitmapDisplayer(20)).build();
	}

	@Override
	public int getCount() {
		return cbs.size();
	}

	@Override
	public Object getItem(int arg0) {
		return cbs.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View converView, ViewGroup arg2) {
		final CommentBase cb = cbs.get(arg0);
		converView = layoutInflater.inflate(R.layout.item_comment, null);
		final ImageView comment_icon = (ImageView) converView.findViewById(R.id.comment_icon);
		TextView comment_username = (TextView) converView.findViewById(R.id.comment_username);
		TextView comment_content = (TextView) converView.findViewById(R.id.comment_content);

		if (cb.getTo_user_name() == null || "".equals(cb.getTo_user_name())) {
			comment_username.setText(cb.getUser_name());
		} else {
			comment_username.setText(cb.getUser_name() + " @" + cb.getTo_user_name());
		}
		comment_content.setText(cb.getContent());
		imageLoader.displayImage(AppConstants.HTTPURL.serverIP + cb.getUser_avatar(), comment_icon, options, displayListener);

		converView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent toCommentActivity = new Intent(mActivity, CommentActivity.class);
				Bundle bundle = new Bundle();
				bundle.putBoolean("isRe", true);
				bundle.putInt("paper_id", paper_id);
				bundle.putSerializable("cb", cb);
				toCommentActivity.putExtras(bundle);
				mActivity.startActivityForResult(toCommentActivity, 101);
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
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

}
