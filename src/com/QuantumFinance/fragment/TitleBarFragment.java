package com.QuantumFinance.fragment;

import com.QuantumFinance.BaiduMTJ.BaiduMTJFragment;
import com.QuantumFinance.ui.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class TitleBarFragment extends BaiduMTJFragment implements OnClickListener {

	private TextView title_one_name;
	private View view;

	@Override
	public void setArguments(Bundle args) {
		super.setArguments(args);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_maintitle, null);
		setUpView();
		return view;
	}

	private void setUpView() {
		title_one_name = (TextView) view.findViewById(R.id.title_one_name);
	}

	// 1是搜索 2是壁纸 3是锁屏 4是视频 5是我的 6是设置 7是关于
	public void setTitleText(int fragmentNum) {
		switch (fragmentNum) {
		case 1:
			title_one_name.setText("推广产品");
			break;
		case 2:
			title_one_name.setText("专家点评");
			break;
		case 3:
			title_one_name.setText("历史回顾");
			break;
		case 4:
			title_one_name.setText("个人中心");
			break;
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {

		}
	}

}
