package com.QuantumFinance.ui.adapter;

import java.util.List;

import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.net.AsyncImageLoader;
import com.QuantumFinance.net.AsyncImageLoader.ImageCallback;
import com.QuantumFinance.net.base.PaperBase;
import com.QuantumFinance.net.base.RecommendBase;
import com.QuantumFinance.ui.PaperInfoActivity;
import com.QuantumFinance.ui.R;
import com.QuantumFinance.ui.RecommendInfoActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PaperAdapter extends BaseAdapter {
	private List<PaperBase> pbs;
	private Context mContext;
	private LayoutInflater layoutInflater;
	private AsyncImageLoader imageLoader;

	public PaperAdapter(List<PaperBase> pbs, Context mContext) {
		this.pbs = pbs;
		this.mContext = mContext;
		this.layoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return pbs.size();
	}

	@Override
	public Object getItem(int arg0) {
		return pbs.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View converView, ViewGroup arg2) {
		final PaperBase pb = pbs.get(arg0);
		converView = layoutInflater.inflate(R.layout.item_paper, null);
		final ImageView item_paper_logo = (ImageView) converView.findViewById(R.id.item_paper_logo);
		TextView item_paper_title = (TextView) converView.findViewById(R.id.item_paper_title);
		TextView item_paper_comment = (TextView) converView.findViewById(R.id.item_paper_comment);
		TextView item_paper_view = (TextView) converView.findViewById(R.id.item_paper_view);
		TextView item_paper_content = (TextView) converView.findViewById(R.id.item_paper_content);

		item_paper_comment.setText("评论:" + pb.getComments());
		item_paper_view.setText("浏览:" + pb.getView_count());
		item_paper_content.setText(pb.getContent());
		item_paper_title.setText(pb.getTitle());

		imageLoader = new AsyncImageLoader();
		imageLoader.loadDrawable(mContext, AppConstants.HTTPURL.serverIP + pb.getLogo(), new ImageCallback() {
			@Override
			public void imageLoaded(Bitmap bm, String imageUrl) {
				if (bm != null)
					item_paper_logo.setImageBitmap(bm);
			}
		}, "paper", pb.getId() + "");

		converView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent toPaperInfo = new Intent(mContext, PaperInfoActivity.class);
				toPaperInfo.putExtra("pb", pb);
				mContext.startActivity(toPaperInfo);
			}
		});
		return converView;
	}

}
