package com.QuantumFinance.ui;

import java.util.List;

import com.QuantumFinance.BaiduMTJ.BaiduMTJActivity;
import com.QuantumFinance.db.CollectDAO;
import com.QuantumFinance.net.base.PaperBase;
import com.QuantumFinance.ui.adapter.PaperAdapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class ICollectActivity extends BaiduMTJActivity {

	private ListView icollect_listview;
	private CollectDAO collectDAO;
	private List<PaperBase> pbs;
	private PaperAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icollect);

		icollect_listview = (ListView) this.findViewById(R.id.icollect_listview);
		collectDAO = new CollectDAO(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		pbs = collectDAO.getAll();
		adapter = new PaperAdapter(pbs, this, mHandler);
		icollect_listview.setAdapter(adapter);
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			showDeleteDialog(msg.what);
			super.handleMessage(msg);
		}
	};
	
	private void showDeleteDialog(final int id) {
		final String items[] = { "删除？" };
		new AlertDialog.Builder(this).setTitle(null).setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				switch (which) {
				case 0:
					collectDAO.delete("" + id);
					pbs = collectDAO.getAll();
					adapter = new PaperAdapter(pbs, ICollectActivity.this, mHandler);
					icollect_listview.removeAllViewsInLayout();
					icollect_listview.setAdapter(adapter);
					break;
				}
			}
		}).show();

	}
}
