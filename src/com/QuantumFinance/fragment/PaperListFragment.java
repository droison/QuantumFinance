package com.QuantumFinance.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.QuantumFinance.BaiduMTJ.BaiduMTJFragment;
import com.QuantumFinance.Thread.ThreadExecutor;
import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.net.GetList;
import com.QuantumFinance.ui.MainActivity;
import com.QuantumFinance.ui.R;
import com.QuantumFinance.util.DialogUtil;

import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PaperListFragment extends BaiduMTJFragment {

	private MainActivity parentActivity;
	private View root;
	private DialogUtil dialogUtil;
	private LinearLayout pull_list_layout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_pull_list, null);
		setUpView();
		initData();
		return root;
	}

	private void initData() {
		ThreadExecutor.execute(new GetList(parentActivity, paperListHandler, AppConstants.HTTPURL.paperlistUrl, 4));
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
			switch (msg.what) {
			case AppConstants.HANDLER_MESSAGE_NORMAL:

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


}