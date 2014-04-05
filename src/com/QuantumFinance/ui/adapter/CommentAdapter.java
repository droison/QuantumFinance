package com.QuantumFinance.ui.adapter;

import java.util.List;

import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.net.AsyncImageLoader;
import com.QuantumFinance.net.AsyncImageLoader.ImageCallback;
import com.QuantumFinance.net.base.CommentBase;
import com.QuantumFinance.ui.CommentActivity;
import com.QuantumFinance.ui.R;

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
	private AsyncImageLoader imageLoader;
	private int paper_id;

	public CommentAdapter(List<CommentBase> cbs, int paper_id, Activity mActivity) {
		this.cbs = cbs;
		this.mActivity = mActivity;
		this.layoutInflater = LayoutInflater.from(mActivity);
		this.paper_id = paper_id;
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

		comment_username.setText(cb.getUser_name());

		if (cb.getTo_user_name() == null || "".equals(cb.getTo_user_name())) {
			comment_content.setText(cb.getContent());
		} else {
			comment_username.setText("@" + cb.getTo_user_name() + " " + cb.getContent());
		}

		imageLoader = new AsyncImageLoader();
		imageLoader.loadDrawable(mActivity, AppConstants.HTTPURL.serverIP + cb.getUser_avata(), new ImageCallback() {
			@Override
			public void imageLoaded(Bitmap bm, String imageUrl) {
				if (bm != null)
					comment_icon.setImageBitmap(bm);
			}
		}, "icon", cb.getId() + "");

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

}
