package com.QuantumFinance.ui.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.net.AsyncImageLoader;
import com.QuantumFinance.net.AsyncImageLoader.ImageCallback;
import com.QuantumFinance.net.base.HistoryBase;
import com.QuantumFinance.net.base.PaperBase;
import com.QuantumFinance.net.base.RecommendBase;
import com.QuantumFinance.ui.PaperInfoActivity;
import com.QuantumFinance.ui.R;
import com.QuantumFinance.ui.RecommendInfoActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HistoryAdapter extends BaseAdapter {
	private List<HistoryBase> hbs;
	private Context mContext;
	private LayoutInflater layoutInflater;
	private AsyncImageLoader imageLoader;
	private SimpleDateFormat sdf;

	public HistoryAdapter(List<HistoryBase> hbs, Context mContext) {
		this.hbs = hbs;
		this.mContext = mContext;
		this.layoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return hbs.size();
	}

	@Override
	public HistoryBase getItem(int arg0) {
		return hbs.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View converView, ViewGroup arg2) {
		final HistoryBase hb = hbs.get(arg0);
		converView = layoutInflater.inflate(R.layout.item_history, null);
		final ImageView item_history_logo = (ImageView) converView.findViewById(R.id.item_history_logo);
		TextView item_history_time = (TextView) converView.findViewById(R.id.item_history_time);
		TextView item_history_content = (TextView) converView.findViewById(R.id.item_history_content);

		item_history_content.setText(hb.getTitle());
		if (sdf == null) {
			sdf = new SimpleDateFormat("hh:mm");
		}
		item_history_time.setText(sdf.format(hb.getUpdated_at()));
		imageLoader = new AsyncImageLoader();

		converView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		return converView;
	}

}
