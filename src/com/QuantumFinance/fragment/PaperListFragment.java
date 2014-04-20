package com.QuantumFinance.fragment;

import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.QuantumFinance.BaiduMTJ.BaiduMTJFragment;
import com.QuantumFinance.Thread.ThreadExecutor;
import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.net.GetData;
import com.QuantumFinance.net.base.PaperBase;
import com.QuantumFinance.ui.MainActivity;
import com.QuantumFinance.ui.R;
import com.QuantumFinance.util.DialogUtil;
import com.QuantumFinance.ui.adapter.PaperAdapter;
import com.QuantumFinance.ui.component.PullToRefreshView;

import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PaperListFragment extends BaiduMTJFragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {
	private boolean isGridRefresh = false;
	private boolean isGridLoadMore = false;
	private int page = 1;
	private MainActivity parentActivity;
	private PullToRefreshView root;
	private DialogUtil dialogUtil;
	private LinearLayout pull_list_layout;
	private PaperAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		root = (PullToRefreshView) inflater.inflate(R.layout.fragment_pull_list, null);
		setUpView();
		initData();
		setUpListener();
		
		return root;
	}

	private void setUpListener() {
		root.setOnHeaderRefreshListener(this);
		root.setOnFooterRefreshListener(this);
	}

	private void initData() {
		ThreadExecutor.execute(new GetData(parentActivity, paperListHandler, AppConstants.HTTPURL.paperlist + page, 4));
		dialogUtil.showProgressDialog(parentActivity, "正在更新数据...");
	}

	public void setUpView() {

		if (getActivity() != null && getActivity() instanceof MainActivity)
			parentActivity = (MainActivity) getActivity();

		if (parentActivity == null)
			return;

		dialogUtil = new DialogUtil();
		pull_list_layout = (LinearLayout) root.findViewById(R.id.pull_list_layout);

	}

	private Handler paperListHandler = new Handler() {
		public void handleMessage(Message msg) {
			dialogUtil.dismissProgressDialog();
			completeGridRefresh();
			switch (msg.what) {
			case AppConstants.HANDLER_MESSAGE_NORMAL:
				List<PaperBase> paperList = (List<PaperBase>) msg.obj;
				if (page == 1) {
					pull_list_layout.removeAllViews();
				}
				
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				lp.setMargins(10, 10, 10, 0);
				
				adapter = new PaperAdapter(paperList, parentActivity);
				int count = adapter.getCount();
				for (int i = 0; i < count; i++) {
					View v = adapter.getDropDownView(i, null, null);
//					v.setLayoutParams(lp);
					pull_list_layout.addView(v);
				}
				if (paperList.size() == 0) {
					root.onFooterShow("已到最后了", false);
					if (pull_list_layout.getChildCount() != 0)
						Toast.makeText(parentActivity, "已到最后了", Toast.LENGTH_SHORT).show();
				}
				break;
			case AppConstants.HANDLER_HTTPSTATUS_ERROR:
				Toast.makeText(parentActivity, "网络访问出错", Toast.LENGTH_SHORT).show();
				break;
			case AppConstants.HANDLER_MESSAGE_NONETWORK:
				dialogUtil.showNoNetWork(parentActivity);
				break;
			}
		};
	};

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		isGridLoadMore = true;
		page++;
		ThreadExecutor.execute(new GetData(parentActivity, paperListHandler, AppConstants.HTTPURL.paperlist + page, 4));
		System.out.println("onFooterRefresh");
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		isGridRefresh = true;
		page = 1;
		ThreadExecutor.execute(new GetData(parentActivity, paperListHandler, AppConstants.HTTPURL.paperlist + page, 4));
		System.out.println("onHeaderRefresh");
	}

	// 此处用户完成“刷新”和“更多”后的头部和尾部的UI变化
	void completeGridRefresh() {

		if (isGridRefresh) {
			root.onHeaderRefreshComplete("上次刷新时间是:" + new Date().toLocaleString());
			isGridRefresh = false;
		}
		if (isGridLoadMore) {
			root.onFooterRefreshComplete();
			isGridLoadMore = false;
		}

	}

}