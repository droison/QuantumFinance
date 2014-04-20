package com.QuantumFinance.ui.adapter;

import com.QuantumFinance.ui.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EvaluateAdapter extends BaseAdapter {
	String[] strs;
	private Activity mActivity;
	private LayoutInflater layoutInflater;

	public EvaluateAdapter(String[] strs, Activity mActivity) {
		this.strs = strs;
		this.mActivity = mActivity;
		this.layoutInflater = LayoutInflater.from(mActivity);
	}

	@Override
	public int getCount() {
		return strs.length;
	}

	@Override
	public String getItem(int arg0) {
		return strs[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View converView, ViewGroup arg2) {
		String str = strs[arg0];
		converView = layoutInflater.inflate(R.layout.item_eva, null);
		TextView item_eva_text = (TextView) converView.findViewById(R.id.item_eva_text);
		if (arg0 % 2 == 1) {
			item_eva_text.setBackgroundResource(R.drawable.eva_item_bg2);
		}
		item_eva_text.setText(str);
		return converView;
	}

}
