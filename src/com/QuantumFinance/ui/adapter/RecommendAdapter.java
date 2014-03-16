package com.QuantumFinance.ui.adapter;

import java.util.List;

import com.QuantumFinance.net.base.RecommendBase;
import com.QuantumFinance.ui.R;
import com.QuantumFinance.ui.RecommendInfoActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RecommendAdapter extends BaseAdapter {
	private List<RecommendBase> rbs;
	private Context mContext;
	private LayoutInflater layoutInflater;

	public RecommendAdapter(List<RecommendBase> rbs, Context mContext) {
		this.rbs = rbs;
		this.mContext = mContext;
		this.layoutInflater = LayoutInflater.from(mContext);
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
		final ImageView item_recommend_logo = (ImageView) converView.findViewById(R.id.item_recommend_logo);
		TextView item_recommend_deadline = (TextView) converView.findViewById(R.id.item_recommend_deadline);
		TextView item_recommend_eair = (TextView) converView.findViewById(R.id.item_recommend_eair);
		TextView item_recommend_schedule = (TextView) converView.findViewById(R.id.item_recommend_schedule);
		TextView item_recommend_title = (TextView) converView.findViewById(R.id.item_recommend_title);
		ProgressBar item_recommend_progressbar = (ProgressBar) converView.findViewById(R.id.item_recommend_progressbar);
		item_recommend_deadline.setText(rb.getDeadline());
		item_recommend_eair.setText(rb.getEair());
		item_recommend_schedule.setText(rb.getSchedule());
		item_recommend_title.setText(rb.getTitle());
		if (rb.getSchedule().equals("无")) {
			item_recommend_progressbar.setProgress(0);
		} else {
			float pro = Float.valueOf(rb.getSchedule().replace("%", ""));
			item_recommend_progressbar.setProgress((int) pro);
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

}
