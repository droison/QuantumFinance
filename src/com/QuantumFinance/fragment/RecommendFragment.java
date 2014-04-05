package com.QuantumFinance.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.QuantumFinance.BaiduMTJ.BaiduMTJFragment;
import com.QuantumFinance.Thread.ThreadExecutor;
import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.net.AsyncImageLoader;
import com.QuantumFinance.net.AsyncImageLoader.ImageCallback;
import com.QuantumFinance.net.GetData;
import com.QuantumFinance.net.base.PPTBase;
import com.QuantumFinance.net.base.RecommendBase;
import com.QuantumFinance.ui.MainActivity;
import com.QuantumFinance.ui.R;
import com.QuantumFinance.ui.RecommendInfoActivity;
import com.QuantumFinance.ui.PaperInfoActivity;
import com.QuantumFinance.ui.adapter.RecommendAdapter;
import com.QuantumFinance.util.DialogUtil;
import com.QuantumFinance.util.DpSpDip2Px;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RecommendFragment extends BaiduMTJFragment implements OnClickListener {

	private ViewPager viewPager;
	private List<ImageView> imageViews;
	private List<View> dots;
	private int currentItem = 0;
	private ScheduledExecutorService scheduledExecutorService;

	private MainActivity parentActivity;
	private View root;
	private RelativeLayout recommend_tab_layout1, recommend_tab_layout2;
	private TextView recommend_tab_text1, recommend_tab_text2;
	private LinearLayout dot_layout;

	private String serverUrl;
	private DialogUtil dialogUtil;
	private LinearLayout recommend_list;
	private String pptUrl;
	private AsyncImageLoader asynImageLoader;

	private RecommendAdapter recommendAdapter;
	private int type = 2; // 1是稳健型，2是激进型 激进型=radical，稳健型＝solid
	private DpSpDip2Px dp2sp;

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		root = inflater.inflate(R.layout.fragment_recommend, null);
		setUpView();
		setUpListener();
		initData();
		return root;
	}

	private void initData() {
		ThreadExecutor.execute(new GetData(parentActivity, pptHandler, pptUrl, 3));
		ThreadExecutor.execute(new GetData(parentActivity, recommendListHandler, serverUrl, 1));
		dialogUtil.showProgressDialog(parentActivity, "正在更新数据...");
	}

	Handler pptHandler = new Handler() {
		public void handleMessage(Message msg) {
			dialogUtil.dismissProgressDialog();
			switch (msg.what) {
			case AppConstants.HANDLER_MESSAGE_NORMAL:
				imageViews = new ArrayList<ImageView>();
				dots = new ArrayList<View>();
				List<PPTBase> pbs = (List<PPTBase>) msg.obj;
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(10, 10);
				lp.setMargins(5, 0, 5, 0);

				for (int i = 0; i < pbs.size(); i++) {
					final PPTBase pb = pbs.get(i);
					final ImageView imageView = new ImageView(parentActivity);
					imageView.setImageResource(R.drawable.image_default);
					asynImageLoader.loadDrawable(parentActivity, AppConstants.HTTPURL.serverIP + pb.getPpt_logo(), new ImageCallback() {

						@Override
						public void imageLoaded(Bitmap bm, String imageUrl) {
							if (bm != null)
								imageView.setImageBitmap(bm);
						}
					}, "ppt", "ppt_" + pb.getId() + ".jpg");
					imageView.setScaleType(ScaleType.CENTER_CROP);

					imageView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							Intent toIntent;
							if (pb.getPpt_type().equals("comment")) {
								toIntent = new Intent(parentActivity, PaperInfoActivity.class);
							} else {
								toIntent = new Intent(parentActivity, RecommendInfoActivity.class);
							}
							toIntent.putExtra("PPTBase",pb);
							toIntent.putExtra("isPPT", true);
							parentActivity.startActivity(toIntent);
						}
					});
					imageViews.add(imageView);

					View view = new View(parentActivity);
					if (i == 0) {
						view.setBackgroundResource(R.drawable.dot_focused);
					} else {
						view.setBackgroundResource(R.drawable.dot_normal);
					}
					view.setLayoutParams(lp);
					dot_layout.addView(view);
					dots.add(view);
				}

				viewPager.setAdapter(new MyAdapter());

				scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
				scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2, TimeUnit.SECONDS);
				break;
			case AppConstants.HANDLER_HTTPSTATUS_ERROR:
				Toast.makeText(parentActivity, "ppt访问出错", 1).show();
				break;
			case AppConstants.HANDLER_MESSAGE_NONETWORK:
				dialogUtil.showNoNetWork(parentActivity);
				break;
			}
		};
	};

	public void setUpView() {
		parentActivity = (MainActivity) getActivity();

		if (parentActivity == null)
			return;

		dp2sp = new DpSpDip2Px(parentActivity);
		viewPager = (ViewPager) root.findViewById(R.id.recommend_ppt);
		viewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dp2sp.getPPTHigh()));
		dialogUtil = new DialogUtil();
		asynImageLoader = new AsyncImageLoader();
		recommend_list = (LinearLayout) root.findViewById(R.id.recommend_list);
		recommend_tab_layout2 = (RelativeLayout) root.findViewById(R.id.recommend_tab_layout2);
		recommend_tab_layout1 = (RelativeLayout) root.findViewById(R.id.recommend_tab_layout1);
		dot_layout = (LinearLayout) root.findViewById(R.id.dot_layout);
		recommend_tab_text1 = (TextView) root.findViewById(R.id.recommend_tab_text1);
		recommend_tab_text2 = (TextView) root.findViewById(R.id.recommend_tab_text2);

	}

	private void setUpListener() {
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
		recommend_tab_layout2.setOnClickListener(this);
		recommend_tab_layout1.setOnClickListener(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		if (scheduledExecutorService != null)
			scheduledExecutorService.shutdown();
		super.onDestroy();
	}

	private Handler recommendListHandler = new Handler() {
		public void handleMessage(Message msg) {
			dialogUtil.dismissProgressDialog();
			switch (msg.what) {
			case AppConstants.HANDLER_MESSAGE_NORMAL:
				List<RecommendBase> rbs = (List<RecommendBase>) msg.obj;
				if (rbs == null || rbs.size() == 0) {
					Toast.makeText(parentActivity, "没有数据", Toast.LENGTH_SHORT).show();
				} else {
					recommend_list.removeAllViews();
					recommendAdapter = new RecommendAdapter(rbs, parentActivity);
					int count = recommendAdapter.getCount();
					for (int i = 0; i < count; i++) {
						View v = recommendAdapter.getDropDownView(i, null, null);
						recommend_list.addView(v);
					}
					switchTab();
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

	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget();
			}
		}

	}

	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;

		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	/**
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(imageViews.get(arg1));
			return imageViews.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.recommend_tab_layout2:
			if (type == 1) {
				dialogUtil.showProgressDialog(parentActivity, "正在获取数据...");
				ThreadExecutor.execute(new GetData(parentActivity, recommendListHandler, serverUrl, 2));
			}
			break;
		case R.id.recommend_tab_layout1:
			if (type == 2) {
				dialogUtil.showProgressDialog(parentActivity, "正在获取数据...");
				ThreadExecutor.execute(new GetData(parentActivity, recommendListHandler, serverUrl, 1));
			}
			break;
		}
	}

	private void switchTab() {
		if (type == 1) {
			recommend_tab_layout2.setBackgroundResource(R.drawable.item_recommend_bg1);
			recommend_tab_layout1.setBackgroundDrawable(null);
			recommend_tab_text1.setTextColor(Color.rgb(31, 141, 215));
			recommend_tab_text2.setTextColor(Color.WHITE);
			// 切换tab样式
			type = 2;
		} else {
			recommend_tab_layout1.setBackgroundResource(R.drawable.item_recommend_bg1);
			recommend_tab_layout2.setBackgroundDrawable(null);
			recommend_tab_text1.setTextColor(Color.WHITE);
			recommend_tab_text2.setTextColor(Color.rgb(31, 141, 215));
			// 切换tab样式
			type = 1;
		}
	}

}