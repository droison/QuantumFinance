package com.QuantumFinance.ui;

import com.QuantumFinance.BaiduMTJ.BaiduMTJActivity;
import com.QuantumFinance.Thread.ThreadExecutor;
import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.net.PostData;
import com.QuantumFinance.net.base.CommentBase;
import com.QuantumFinance.net.base.PostCommentBase;
import com.QuantumFinance.net.base.Result;
import com.QuantumFinance.util.DialogUtil;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CommentActivity extends BaiduMTJActivity {

	private EditText comment_content;

	private Button btn_ok, btn_cancel;
	private TextView comment_prompt_text, comment_rename;

	private Bundle bundle;
	private boolean isRe;
	private CommentBase comment;
	private DialogUtil dialogUtil;
	private int paper_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_comment);

		setUpView();
		initData();
		setUpListener();

	}

	private void setUpView() {
		comment_content = (EditText) findViewById(R.id.comment_content);
		comment_prompt_text = (TextView) findViewById(R.id.comment_prompt_text);
		comment_rename = (TextView) findViewById(R.id.comment_rename);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_ok = (Button) findViewById(R.id.btn_ok);

		dialogUtil = new DialogUtil();
	}

	private void initData() {
		bundle = getIntent().getExtras();
		isRe = bundle.getBoolean("isRe", false);
		paper_id = bundle.getInt("paper_id");
		if (isRe) {
			comment = (CommentBase) bundle.getSerializable("cb");
			comment_prompt_text.setText("我要回复");
			comment_rename.setText("@" + comment.getUser_name());
		} else {
			comment_prompt_text.setText("我要评论");
			comment_rename.setVisibility(View.GONE);
		}
	}

	private void setUpListener() {
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});

		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String content = comment_content.getText().toString();
				if (TextUtils.isEmpty(content)) {
					dialogUtil.showToast(CommentActivity.this, "请输入评论内容");
				} else {
					PostCommentBase pcb = new PostCommentBase();
					pcb.setContent(content);
					if (comment != null) {
						pcb.setTo_user_id(comment.getUser_id());
					}
					pcb.setUser_id(1);
					pcb.setComment_id(paper_id);
					dialogUtil.showProgressDialog(CommentActivity.this, isRe ? "正在回复" : "正在评论");
					ThreadExecutor.execute(new PostData(CommentActivity.this, commentHandler, pcb,1));
					
					comment_content.clearFocus();
				}
			}
		});
	}

	private Handler commentHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			dialogUtil.dismissProgressDialog();
			switch (msg.what) {
			case AppConstants.HANDLER_MESSAGE_NORMAL:
				Result cr = (Result) msg.obj;
				if(cr.isStatus()){
					setResult(RESULT_OK);
				}else{
					setResult(RESULT_FAILE);
				}
				finish();
				break;
			case AppConstants.HANDLER_HTTPSTATUS_ERROR:
				Toast.makeText(CommentActivity.this, "网络访问出错", Toast.LENGTH_SHORT).show();
				break;
			case AppConstants.HANDLER_MESSAGE_NONETWORK:
				dialogUtil.showNoNetWork(CommentActivity.this);
				break;
			}
		};
	};
}
