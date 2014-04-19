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
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends BaiduMTJFragmentActivity {

	private TitleBarFragment titleBarFragment;
	private MainTabFragment mainTabFragment;
	private RecommendFragment recommendFragment;
	private PaperListFragment paperListFragment;
	private HistoryFragment historyFragment;
	private ICenterFragment iCenterFragment;

	private ImageView start_bg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setFullScreen();
		setContentView(R.layout.activity_main);
		setUp();
		UpdateHandler updateHandler = new UpdateHandler(this);
		ThreadExecutor.execute(new CheckVersionService(this, updateHandler));
	}

	private void setFullScreen() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

	}

	// [代码] 退出全屏函数：
	private void quitFullScreen() {
		final WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setAttributes(attrs);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
	}

	private void setUp() {
		start_bg = (ImageView) this.findViewById(R.id.start_bg);
		Handler startHandler = new Handler();
		startHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start_bg.setBackgroundDrawable(null);
				quitFullScreen();
			}
		}, 3000);

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
				getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, historyFragment).commit();
			} else {
				getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, historyFragment).commit();
			}
			break;
		case 4:
			if (iCenterFragment == null) {
				iCenterFragment = new ICenterFragment();
				getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, iCenterFragment).commit();
			} else {
				getSupportFragmentManager().beginTransaction().replace(R.id.main_content_frame, iCenterFragment).commit();
			}
			break;
		default:
			break;
		}
	}

	boolean mIsCanEixt;

	/**
	 * 　　* Fragment跳转 　　* @param fm 　　* @param fragmentClass 　　* @param tag 　　* @param
	 * args 　　
	 */
	public void turnToFragment(FragmentManager fm, Class fragmentClass, String tag, Bundle args) {
		mIsCanEixt = false;
		Fragment fragment = fm.findFragmentByTag(tag);
		boolean isFragmentExist = true;
		if (fragment == null) {
			try {
				isFragmentExist = false;
				fragment = (Fragment) fragmentClass.newInstance();
				fragment.setArguments(new Bundle());
			} catch (java.lang.InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (fragment.isAdded()) {
				return;
			}

		}
		if (args != null && !args.isEmpty()) {
			fragment.getArguments().putAll(args);
		}
		FragmentTransaction ft = fm.beginTransaction();
		ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
		if (isFragmentExist) {
			fm.popBackStack();
//			ft.replace(R.id.main_content_frame, fragment);
		} else {
			ft.replace(R.id.main_content_frame, fragment, tag);
			ft.addToBackStack(tag);
			ft.commitAllowingStateLoss();
		}
	}

}
