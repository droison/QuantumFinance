package com.QuantumFinance.ui;

import com.QuantumFinance.BaiduMTJ.BaiduMTJFragmentActivity;
import com.QuantumFinance.Thread.ThreadExecutor;
import com.QuantumFinance.fragment.HistoryFragment;
import com.QuantumFinance.fragment.ICenterFragment;
import com.QuantumFinance.fragment.MainTabFragment;
import com.QuantumFinance.fragment.PaperListFragment;
import com.QuantumFinance.fragment.RecommendFragment;
import com.QuantumFinance.fragment.TitleBarFragment;
import com.QuantumFinance.updateapp.CheckVersionService;
import com.QuantumFinance.updateapp.UpdateHandler;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.Toast;

public class MainActivity extends BaiduMTJFragmentActivity {

	private TitleBarFragment titleBarFragment;
	private MainTabFragment mainTabFragment;
	private RecommendFragment recommendFragment;
	private PaperListFragment paperListFragment;
	private HistoryFragment historyFragment;
	private ICenterFragment iCenterFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setUp();

		UpdateHandler updateHandler = new UpdateHandler(this);
		ThreadExecutor.execute(new CheckVersionService(this, updateHandler));
	}

	private void setUp() {
		titleBarFragment = new TitleBarFragment();
		mainTabFragment = new MainTabFragment();
		getSupportFragmentManager().beginTransaction().replace(R.id.main_title_frame, titleBarFragment).commit();
		getSupportFragmentManager().beginTransaction().replace(R.id.main_tab_frame, mainTabFragment).commit();
	}

	private long backTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long curTime = System.currentTimeMillis();
			if (curTime - backTime > 2000) {
				backTime = curTime;
				Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
			} else {
				finish();
			}
		}
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	protected void onPause() {
		super.onPause();
	}

	public void switchContent(int tabnum) {
		titleBarFragment.setTitleText(tabnum);
		mainTabFragment.switchTab(tabnum);
		switch (tabnum) {
		case 1:
			if (recommendFragment == null) {
				recommendFragment = new RecommendFragment();
				getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, recommendFragment).commit();
			} else {
				getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, recommendFragment).commit();
			}
			break;
		case 2:
			if (paperListFragment == null) {
				paperListFragment = new PaperListFragment();
				getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, paperListFragment).commit();

			} else {
				getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, paperListFragment).commit();
			}
			break;
		case 3:
			if (historyFragment == null) {
				historyFragment = new HistoryFragment();
			} else {
				getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, historyFragment).commit();
			}
			break;
		case 4:
			if (iCenterFragment == null) {
				iCenterFragment = new ICenterFragment();
			} else {
				getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, iCenterFragment).commit();
			}
			break;
		default:
			break;
		}
	}

}
