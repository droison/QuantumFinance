package com.QuantumFinance.ui.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListViewInScrollView extends ListView {

	public ListViewInScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ListViewInScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ListViewInScrollView(Context context, AttributeSet attrs, int i) {
		super(context, attrs, i);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		// TODO Auto-generated method stub
		super.setAdapter(adapter);
		setListViewHeightBasedOnChildren(this);
	}

	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
}
