package com.QuantumFinance.ui;

import java.util.List;

import com.QuantumFinance.BaiduMTJ.BaiduMTJActivity;
import com.QuantumFinance.db.CollectDAO;
import com.QuantumFinance.net.base.PaperBase;
import com.QuantumFinance.ui.adapter.PaperAdapter;

import android.os.Bundle;
import android.widget.ListView;

public class ICollectActivity extends BaiduMTJActivity {
	
	private ListView icollect_listview;
	private CollectDAO collectDAO;

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
		List<PaperBase> pbs = collectDAO.getAll();
		PaperAdapter adapter = new PaperAdapter(pbs, this);
		icollect_listview.setAdapter(adapter);
	}
}
