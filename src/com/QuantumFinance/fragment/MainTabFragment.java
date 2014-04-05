package com.QuantumFinance.fragment;

import com.QuantumFinance.BaiduMTJ.BaiduMTJFragment;
import com.QuantumFinance.ui.MainActivity;
import com.QuantumFinance.ui.R;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainTabFragment extends BaiduMTJFragment {

	private View view;
	private MainActivity parentActivity = null;

	/*
	 * 选项卡部分
	 */
	private RelativeLayout bottom_tab_layout4, bottom_tab_layout3, bottom_tab_layout2, bottom_tab_layout1;
	private TextView bottom_tab_text1, bottom_tab_text2, bottom_tab_text3, bottom_tab_text4;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_maintab, null);
		setUpView();
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getActivity() != null && getActivity() instanceof MainActivity) {
			parentActivity = (MainActivity) getActivity();
		}
	}

	private void setUpView() {
		bottom_tab_layout4 = (RelativeLayout) view.findViewById(R.id.bottom_tab_layout4);
		bottom_tab_layout3 = (RelativeLayout) view.findViewById(R.id.bottom_tab_layout3);
		bottom_tab_layout2 = (RelativeLayout) view.findViewById(R.id.bottom_tab_layout2);
		bottom_tab_layout1 = (RelativeLayout) view.findViewById(R.id.bottom_tab_layout1);
		bottom_tab_text1 = (TextView) view.findViewById(R.id.bottom_tab_text1);
		bottom_tab_text2 = (TextView) view.findViewById(R.id.bottom_tab_text2);
		bottom_tab_text3 = (TextView) view.findViewById(R.id.bottom_tab_text3);
		bottom_tab_text4 = (TextView) view.findViewById(R.id.bottom_tab_text4);

		bottom_tab_layout4.setOnClickListener(new CheckListener());
		bottom_tab_layout3.setOnClickListener(new CheckListener());
		bottom_tab_layout2.setOnClickListener(new CheckListener());
		bottom_tab_layout1.setOnClickListener(new CheckListener());

		parentActivity.switchContent(1);
	}

	public void switchTab(int viewId) {
		bottom_tab_layout1.setBackgroundColor(Color.rgb(27, 79, 109));
		bottom_tab_layout2.setBackgroundColor(Color.rgb(27, 79, 109));
		bottom_tab_layout3.setBackgroundColor(Color.rgb(27, 79, 109));
		bottom_tab_layout4.setBackgroundColor(Color.rgb(27, 79, 109));
		switch (viewId) {
		case 1:
			bottom_tab_layout1.setBackgroundColor(Color.rgb(14, 159, 222));
			break;
		case 2:
			bottom_tab_layout2.setBackgroundColor(Color.rgb(14, 159, 222));
			break;
		case 3:
			bottom_tab_layout3.setBackgroundColor(Color.rgb(14, 159, 222));
			break;
		case 4:
			bottom_tab_layout4.setBackgroundColor(Color.rgb(14, 159, 222));
			break;
		}
	}

	private class CheckListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			switchTab(arg0.getId());
			switch (arg0.getId()) {

			case R.id.bottom_tab_layout1:

				parentActivity.switchContent(1);

				break;
			case R.id.bottom_tab_layout2:

				parentActivity.switchContent(2);

				break;
			case R.id.bottom_tab_layout3:
				parentActivity.switchContent(3);

				break;
			case R.id.bottom_tab_layout4:
				parentActivity.switchContent(4);
				break;
			default:
				break;
			}
		}
	};

}
