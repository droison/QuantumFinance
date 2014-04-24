package com.QuantumFinance.ui;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;

import com.QuantumFinance.BaiduMTJ.BaiduMTJActivity;
import com.QuantumFinance.Thread.ThreadExecutor;
import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.db.AccountDAO;
import com.QuantumFinance.db.CollectDAO;
import com.QuantumFinance.db.PraiseDAO;
import com.QuantumFinance.net.GetData;
import com.QuantumFinance.net.ShareTask;
import com.QuantumFinance.net.base.CommentBase;
import com.QuantumFinance.net.base.PPTBase;
import com.QuantumFinance.net.base.PaperBase;
import com.QuantumFinance.ui.adapter.CommentAdapter;
import com.QuantumFinance.ui.component.PullToRefreshView;
import com.QuantumFinance.util.DialogUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class PaperInfoActivity extends BaiduMTJActivity implements PullToRefreshView.OnFooterRefreshListener, PullToRefreshView.OnHeaderRefreshListener {

	private TextView paperinfo_comment;
	private WebView paperinfo_content;
	private ImageView paperinfo_logo;
	private TextView paperinfo_time;
	private TextView paperinfo_title;
	private TextView paperinfo_view;

	private RelativeLayout paperinfo_tab_layout1;
	private RelativeLayout paperinfo_tab_layout2;
	private RelativeLayout paperinfo_tab_layout3;
	private RelativeLayout paperinfo_tab_layout4;
	private TextView paperinfo_tab_text1;
	private TextView paperinfo_tab_text2;
	private TextView paperinfo_tab_text3;
	private TextView paperinfo_tab_text4;

	private LinearLayout paperinfo_comment_layout;
	private PullToRefreshView paperinfo_pullview;

	private PaperBase pb;

	private DialogUtil dialogUtil = new DialogUtil();
	private ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	private ImageLoadingListener displayListener = new DisplayListener();
	private SimpleDateFormat sdf;
	private boolean isGridRefresh = false;
	private boolean isGridLoadMore = false;
	private int page = 1;
	private CommentAdapter adapter;
	private CollectDAO collectDAO;
	private PraiseDAO praiseDAO;
	private AccountDAO accountDAO;
	private boolean isPPT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paperinfo);
		setUpView();
		initData();

		ShareSDK.initSDK(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ShareSDK.stopSDK(this);
	}

	private void initData() {
		isPPT = getIntent().getBooleanExtra("isPPT", false);
		if (isPPT) {
			PPTBase pptBase = (PPTBase) getIntent().getSerializableExtra("PPTBase");
			dialogUtil.showProgressDialog(this, "正在读取数据...");
			ThreadExecutor.execute(new GetData(this, paperHandler, AppConstants.HTTPURL.paperInfo + pptBase.getComment_or_product_id(), 6));
		} else {
			pb = (PaperBase) getIntent().getSerializableExtra("pb");
			// sdf = new SimpleDateFormat("MM-dd HH:mm");
			// paperinfo_comment.setText("评论:" + pb.getComments());
			// pb.setContent(pb.getContent().replace("background-color:", ""));
			// paperinfo_content.loadDataWithBaseURL(null, new
			// StringBuilder(AppConstants.FONT_START).append(AppConstants.BODY_START).append(pb.getContent()).append(AppConstants.BODY_END).toString(),
			// AppConstants.TEXT_HTML, AppConstants.UTF8, null);
			// paperinfo_content.setBackgroundColor(getResources().getColor(R.color.paperinfo_content_bg));
			// paperinfo_time.setText(sdf.format(pb.getUpdated_at()));
			// paperinfo_title.setText(pb.getTitle());
			// if (pb.getTitle() == null || pb.getTitle().equals("")) {
			// paperinfo_title.setText("    ");
			// }
			//
			// paperinfo_view.setText("浏览:" + pb.getView_count());
			//
			// if (collectDAO.isExist("" + pb.getId())) {
			// paperinfo_tab_text2.setCompoundDrawablesWithIntrinsicBounds(0,
			// R.drawable.paperinfo_collect_press, 0, 0);
			// }
			// if (praiseDAO.isExist("" + pb.getId())) {
			// paperinfo_tab_text4.setCompoundDrawablesWithIntrinsicBounds(0,
			// R.drawable.paperinfo_praise_press, 0, 0);
			// }
			//
			// imageLoader.displayImage(AppConstants.HTTPURL.serverIP+
			// pb.getLogo(), paperinfo_logo, options, displayListener);

			dialogUtil.showProgressDialog(this, "正在读取数据...");
			ThreadExecutor.execute(new GetData(this, paperHandler, AppConstants.HTTPURL.paperInfo + pb.getId(), 6));
			ThreadExecutor.execute(new GetData(this, commentListHandler, AppConstants.HTTPURL.commentlist + pb.getId() + "&page=" + page, 7));
			setUpListener();
		}
	}

	private Handler paperHandler = new Handler() {
		public void handleMessage(Message msg) {
			dialogUtil.dismissProgressDialog();
			switch (msg.what) {
			case AppConstants.HANDLER_MESSAGE_NORMAL:
				pb = (PaperBase) msg.obj;
				if (pb != null && pb.getId() != 0) {
					sdf = new SimpleDateFormat("MM-dd hh:mm");
					paperinfo_comment.setText("评论:" + pb.getComments());
					pb.setContent(pb.getContent().replace("background-color:", ""));
					paperinfo_content.loadDataWithBaseURL(null, new StringBuilder(AppConstants.FONT_START).append(AppConstants.BODY_START).append(pb.getContent()).append(AppConstants.BODY_END).toString(), AppConstants.TEXT_HTML, AppConstants.UTF8, null);
					paperinfo_content.setBackgroundColor(getResources().getColor(R.color.paperinfo_content_bg));
					paperinfo_time.setText(sdf.format(pb.getUpdated_at()));
					paperinfo_title.setText(pb.getTitle());
					paperinfo_view.setText("浏览:" + pb.getView_count());
					if (collectDAO.isExist("" + pb.getId())) {
						paperinfo_tab_text2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.paperinfo_collect_press, 0, 0);
					}
					if (praiseDAO.isExist("" + pb.getId())) {
						paperinfo_tab_text4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.paperinfo_praise_press, 0, 0);
					}
					imageLoader.displayImage(AppConstants.HTTPURL.serverIP + pb.getLogo(), paperinfo_logo, options, displayListener);

					if (isPPT) {
						ThreadExecutor.execute(new GetData(PaperInfoActivity.this, commentListHandler, AppConstants.HTTPURL.commentlist + pb.getId() + "&page=" + page, 7));
						setUpListener();
					}
				}
				break;
			case AppConstants.HANDLER_HTTPSTATUS_ERROR:
				Toast.makeText(PaperInfoActivity.this, "网络访问出错", Toast.LENGTH_SHORT).show();
				break;
			case AppConstants.HANDLER_MESSAGE_NONETWORK:
				dialogUtil.showNoNetWork(PaperInfoActivity.this);
				break;
			}
		};
	};

	private Handler commentListHandler = new Handler() {
		public void handleMessage(Message msg) {
			dialogUtil.dismissProgressDialog();
			completeGridRefresh();
			switch (msg.what) {
			case AppConstants.HANDLER_MESSAGE_NORMAL:
				List<CommentBase> commentList = (List<CommentBase>) msg.obj;
				if (page == 1) {
					paperinfo_comment_layout.removeAllViews();
				}
				adapter = new CommentAdapter(commentList, pb.getId(), PaperInfoActivity.this);
				int count = adapter.getCount();
				for (int i = 0; i < count; i++) {
					View v = adapter.getDropDownView(i, null, null);
					paperinfo_comment_layout.addView(v);
				}
				if (commentList.size() == 0) {
					paperinfo_pullview.onFooterShow("已到最后了", false);
					if (paperinfo_comment_layout.getChildCount() != 0)
						Toast.makeText(PaperInfoActivity.this, "已到最后了", Toast.LENGTH_SHORT).show();
				}
				if (paperinfo_comment_layout.getChildCount() != 0)
					paperinfo_comment_layout.setBackgroundResource(R.drawable.paperinfo_comment_bg);
				break;
			case AppConstants.HANDLER_HTTPSTATUS_ERROR:
				Toast.makeText(PaperInfoActivity.this, "网络访问出错", Toast.LENGTH_SHORT).show();
				break;
			case AppConstants.HANDLER_MESSAGE_NONETWORK:
				dialogUtil.showNoNetWork(PaperInfoActivity.this);
				break;
			}
		};
	};

	private void setUpListener() {
		paperinfo_pullview.setOnHeaderRefreshListener(this);
		paperinfo_pullview.setOnFooterRefreshListener(this);
		paperinfo_tab_layout1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ShareTask shareTask = new ShareTask(PaperInfoActivity.this, pb);
				shareTask.execute();
			}
		});
		paperinfo_tab_layout2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!collectDAO.isExist("" + pb.getId())) {
					collectDAO.save(pb);
					paperinfo_tab_text2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.paperinfo_collect_press, 0, 0);
				} else {
					collectDAO.delete("" + pb.getId());
					paperinfo_tab_text2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.paperinfo_collect_noraml, 0, 0);
				}
			}
		});
		paperinfo_tab_layout3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toCommentActivity = new Intent(PaperInfoActivity.this, CommentActivity.class);
				Bundle bundle = new Bundle();
				bundle.putBoolean("isRe", false);
				bundle.putInt("paper_id", pb.getId());
				toCommentActivity.putExtras(bundle);
				startActivityForResult(toCommentActivity, 101);
			}
		});
		paperinfo_tab_layout4.setTag("未赞");
		paperinfo_tab_layout4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!praiseDAO.isExist(pb.getId() + "") && "未赞".equals(paperinfo_tab_layout4.getTag())) {
					paperinfo_tab_layout4.setTag("已赞");
					ThreadExecutor.execute(new GetData(PaperInfoActivity.this, praiseHandler, AppConstants.HTTPURL.praise + pb.getId(), 8));
				}
			}
		});
	}

	private Handler praiseHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			paperinfo_tab_layout4.setTag("未赞");
			switch (msg.what) {
			case AppConstants.HANDLER_MESSAGE_NORMAL:
				praiseDAO.save(pb.getId() + "");
				paperinfo_tab_text4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.paperinfo_praise_press, 0, 0);
				break;
			case AppConstants.HANDLER_HTTPSTATUS_ERROR:
				Toast.makeText(PaperInfoActivity.this, "网络访问出错", Toast.LENGTH_SHORT).show();
				break;
			case AppConstants.HANDLER_MESSAGE_NONETWORK:
				dialogUtil.showNoNetWork(PaperInfoActivity.this);
				break;
			}
		};
	};

	private void setUpView() {
		paperinfo_logo = (ImageView) this.findViewById(R.id.paperinfo_logo);

		paperinfo_tab_layout1 = (RelativeLayout) this.findViewById(R.id.paperinfo_tab_layout1);
		paperinfo_tab_layout2 = (RelativeLayout) this.findViewById(R.id.paperinfo_tab_layout2);
		paperinfo_tab_layout3 = (RelativeLayout) this.findViewById(R.id.paperinfo_tab_layout3);
		paperinfo_tab_layout4 = (RelativeLayout) this.findViewById(R.id.paperinfo_tab_layout4);

		paperinfo_comment = (TextView) this.findViewById(R.id.paperinfo_comment);
		paperinfo_content = (WebView) this.findViewById(R.id.paperinfo_content);
		paperinfo_time = (TextView) this.findViewById(R.id.paperinfo_time);
		paperinfo_title = (TextView) this.findViewById(R.id.paperinfo_title);
		paperinfo_view = (TextView) this.findViewById(R.id.paperinfo_view);
		paperinfo_tab_text1 = (TextView) this.findViewById(R.id.paperinfo_tab_text1);
		paperinfo_tab_text2 = (TextView) this.findViewById(R.id.paperinfo_tab_text2);
		paperinfo_tab_text3 = (TextView) this.findViewById(R.id.paperinfo_tab_text3);
		paperinfo_tab_text4 = (TextView) this.findViewById(R.id.paperinfo_tab_text4);

		paperinfo_comment_layout = (LinearLayout) this.findViewById(R.id.paperinfo_comment_layout);
		paperinfo_pullview = (PullToRefreshView) this.findViewById(R.id.paperinfo_pullview);

		collectDAO = new CollectDAO(this);
		accountDAO = new AccountDAO(this);
		praiseDAO = new PraiseDAO(this);

		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.paperinfo_default).showImageForEmptyUri(R.drawable.paperinfo_default).showImageOnFail(R.drawable.paperinfo_default).cacheInMemory(true).cacheOnDisc(true).considerExifParams(true).displayer(new RoundedBitmapDisplayer(20)).build();
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		isGridLoadMore = true;
		page++;
		ThreadExecutor.execute(new GetData(this, commentListHandler, AppConstants.HTTPURL.commentlist + pb.getId() + "&page=" + page, 7));
		System.out.println("onFooterRefresh");
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		isGridRefresh = true;
		page = 1;
		ThreadExecutor.execute(new GetData(this, commentListHandler, AppConstants.HTTPURL.commentlist + pb.getId() + "&page=" + page, 7));
		System.out.println("onHeaderRefresh");
	}

	// 此处用户完成“刷新”和“更多”后的头部和尾部的UI变化
	void completeGridRefresh() {

		if (isGridRefresh) {
			paperinfo_pullview.onHeaderRefreshComplete("上次刷新时间是:" + new Date().toLocaleString());
			isGridRefresh = false;
		}
		if (isGridLoadMore) {
			paperinfo_pullview.onFooterRefreshComplete();
			isGridLoadMore = false;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_CANCELED)
			return;
		if (resultCode == RESULT_OK) {
			isGridRefresh = true;
			page = 1;
			ThreadExecutor.execute(new GetData(this, commentListHandler, AppConstants.HTTPURL.commentlist + pb.getId() + "&page=" + page, 7));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private int getPicHigh(Bitmap bm) {
		DisplayMetrics dm = this.getResources().getDisplayMetrics();
		int mScreenWidth = dm.widthPixels;// 屏幕宽
		int bmHigh = bm.getHeight();
		int bmWidth = bm.getWidth();
		return mScreenWidth * bmHigh / bmWidth;
	}

	class DisplayListener extends SimpleImageLoadingListener {

		final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getPicHigh(loadedImage)));

				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

}
