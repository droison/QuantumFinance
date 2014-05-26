package com.QuantumFinance.ui;

import com.QuantumFinance.BaiduMTJ.BaiduMTJActivity;
import com.QuantumFinance.Thread.ThreadExecutor;
import com.QuantumFinance.constants.AppConstants;
import com.QuantumFinance.db.AccountDAO;
import com.QuantumFinance.db.DbAccount;
import com.QuantumFinance.net.PostData;
import com.QuantumFinance.net.base.PostEva;
import com.QuantumFinance.ui.adapter.EvaluateAdapter;
import com.QuantumFinance.util.DialogUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EvaluateActivity extends BaiduMTJActivity {

	private RelativeLayout eva_layout1, eva_layout2, eva_layout3, eva_layout4, eva_layout5, eva_layout6, eva_layout7;
	private LinearLayout eva_linear1, eva_linear2;
	private TextView eva_text1, eva_text2, eva_text3, eva_text4, eva_text5, eva_text6, eva_text7;
	// String[] strArray = { "稳健型", "激进型", "稳健型", "激进型", "稳健型", "激进型" };
	// String[] strArray2 = { "稳健型2", "激进型22", "稳健型", "激进型", "稳健型", "激进型" };
	String[] strArray3 = { "进取型（可以20%以上损失）", "中庸性(可以10%左右损失)", "保守型（不能承受损失）" };
	String[] strArray4 = { "固定收益类基金", "权益类基金", "P2P信贷理财", "理财产品", "房地产", "信托" };
	String[] strArray5 = { "有房，全额付款", "有房，有车，全额付款", "有房，还贷中", "有房，有车，还贷中", "其他" };
	String[] strArray6 = { "有子女，赡养老人", "无子女，赡养老人", "单身，赡养老人", "其他" };
	private DbAccount account;
	private AccountDAO accountDAO;
	private DialogUtil dialogUtil;
	
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evaluate);
		setUpView();
		setUpListener();
	}

	private void setUpView() {
		sp = getSharedPreferences("evaluate", 0);
		editor = sp.edit();
		
		
		eva_layout1 = (RelativeLayout) this.findViewById(R.id.eva_layout1);
		eva_layout2 = (RelativeLayout) this.findViewById(R.id.eva_layout2);
		eva_layout3 = (RelativeLayout) this.findViewById(R.id.eva_layout3);
		eva_layout4 = (RelativeLayout) this.findViewById(R.id.eva_layout4);
		eva_layout5 = (RelativeLayout) this.findViewById(R.id.eva_layout5);
		eva_layout6 = (RelativeLayout) this.findViewById(R.id.eva_layout6);
		eva_layout7 = (RelativeLayout) this.findViewById(R.id.eva_layout7);
		eva_linear1 = (LinearLayout) this.findViewById(R.id.eva_linear1);
		eva_linear2 = (LinearLayout) this.findViewById(R.id.eva_linear2);

		eva_text1 = (TextView) this.findViewById(R.id.eva_text1);
		eva_text2 = (TextView) this.findViewById(R.id.eva_text2);
		eva_text3 = (TextView) this.findViewById(R.id.eva_text3);
		eva_text4 = (TextView) this.findViewById(R.id.eva_text4);
		eva_text5 = (TextView) this.findViewById(R.id.eva_text5);
		eva_text6 = (TextView) this.findViewById(R.id.eva_text6);
		eva_text7 = (TextView) this.findViewById(R.id.eva_text7);

		// eva_linear1.addView(getExpandView(strArray, eva_text2), 3);
		// eva_linear1.addView(getExpandView(strArray2, eva_text3), 6);

		eva_linear2.addView(getExpandView(strArray3, eva_text4), 1);
		eva_linear2.addView(getExpandView(strArray4, eva_text5), 4);
		eva_linear2.addView(getExpandView(strArray5, eva_text6), 7);
		eva_linear2.addView(getExpandView(strArray6, eva_text7), 10);

		eva_text1.setText(sp.getString("text1", "请输入您的姓名"));
		eva_text2.setText(sp.getString("text2", "请输入您的职业"));
		eva_text3.setText(sp.getString("text3", "年收入（万元）"));
		eva_text4.setText(sp.getString("text4",strArray3[0]));
		eva_text5.setText(sp.getString("text5",strArray4[0]));
		eva_text6.setText(sp.getString("text6",strArray5[0]));
		eva_text7.setText(sp.getString("text7",strArray6[0]));
		
		accountDAO = new AccountDAO(this);
		account = accountDAO.getAccount();
		if (account == null) {
			Intent toLogin = new Intent(EvaluateActivity.this, LoginActivity.class);
			startActivity(toLogin);
			finish();
			}
		dialogUtil = new DialogUtil();	
	}

	private LinearLayout getExpandView(String[] strs, final TextView textView) {
		final LinearLayout listLinear = new LinearLayout(EvaluateActivity.this);
		listLinear.setBackgroundColor(Color.WHITE);
		final EvaluateAdapter adapter = new EvaluateAdapter(strs, EvaluateActivity.this);
		for (int i = 0; i < adapter.getCount(); i++) {
			View v = adapter.getDropDownView(i, null, null);
			final int arg = i;
			v.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					textView.setText(adapter.getItem(arg));
					listLinear.setVisibility(View.GONE);
				}
			});
			listLinear.addView(v);
		}
		listLinear.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
		listLinear.setOrientation(LinearLayout.VERTICAL);
		listLinear.setVisibility(View.GONE);
		return listLinear;
	}

	private void setUpListener() {
		eva_layout1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showInputDialog(0);
			}
		});
		eva_layout2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// if (eva_linear1.getChildAt(3).getVisibility() == View.GONE) {
				// eva_linear1.getChildAt(3).setVisibility(View.VISIBLE);
				// } else {
				// eva_linear1.getChildAt(3).setVisibility(View.GONE);
				// }
				showInputDialog(1);
			}
		});
		eva_layout3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// if (eva_linear1.getChildAt(6).getVisibility() == View.GONE) {
				// eva_linear1.getChildAt(6).setVisibility(View.VISIBLE);
				// } else {
				// eva_linear1.getChildAt(6).setVisibility(View.GONE);
				// }
				showInputDialog(2);
			}
		});
		eva_layout4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (eva_linear2.getChildAt(1).getVisibility() == View.GONE) {
					eva_linear2.getChildAt(1).setVisibility(View.VISIBLE);
				} else {
					eva_linear2.getChildAt(1).setVisibility(View.GONE);
				}

			}
		});
		eva_layout5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (eva_linear2.getChildAt(4).getVisibility() == View.GONE) {
					eva_linear2.getChildAt(4).setVisibility(View.VISIBLE);
				} else {
					eva_linear2.getChildAt(4).setVisibility(View.GONE);
				}
			}
		});
		eva_layout6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (eva_linear2.getChildAt(7).getVisibility() == View.GONE) {
					eva_linear2.getChildAt(7).setVisibility(View.VISIBLE);
				} else {
					eva_linear2.getChildAt(7).setVisibility(View.GONE);
				}
			}
		});
		eva_layout7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (eva_linear2.getChildAt(10).getVisibility() == View.GONE) {
					eva_linear2.getChildAt(10).setVisibility(View.VISIBLE);
				} else {
					eva_linear2.getChildAt(10).setVisibility(View.GONE);
				}
			}
		});
	}

	private void showInputDialog(final int type) {
		String text = null;
		switch (type) {
		case 1:
			text = "职业";
			break;
		case 0:
			text = "姓名";
			break;
		default:
			text = "年收入（万元）";
			break;
		}
		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.dialog_infoupdate, null);
		final EditText infoupdate_content = (EditText) textEntryView.findViewById(R.id.infoupdate_content);
		TextView infoupdate_prompt_text = (TextView) textEntryView.findViewById(R.id.infoupdate_prompt_text);
		infoupdate_content.setHint(text);
		infoupdate_prompt_text.setText(text);
		if(type==2){
			infoupdate_content.setInputType(InputType.TYPE_CLASS_NUMBER);
		}
		AlertDialog dlg = new AlertDialog.Builder(EvaluateActivity.this).setView(textEntryView).setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String str = infoupdate_content.getText().toString();
				if (TextUtils.isEmpty(str)) {
					Toast.makeText(EvaluateActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
				} else {
					switch (type) {
					case 0:
						eva_text1.setText(str);
						break;
					case 1:
						eva_text2.setText(str);
						break;
					default:
						eva_text3.setText(str);
						break;
					}
					// 此处用来更新数据
				}

			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			}
		}).create();
		dlg.show();
	}
	
	Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			dialogUtil.dismissProgressDialog();
			switch (msg.what) {
			case AppConstants.HANDLER_MESSAGE_NORMAL:
				dialogUtil.showToast(EvaluateActivity.this, "评估成功");
				break;
			case AppConstants.HANDLER_MESSAGE_NONETWORK:
				dialogUtil.showNoNetWork(EvaluateActivity.this);
				break;
			case AppConstants.HANDLER_HTTPSTATUS_ERROR:
				dialogUtil.showToast(EvaluateActivity.this, "评估失败，请稍后重试");
				break;
			}
		};
	};
	
	public void evaSubmit(View v){
		String name = eva_text1.getText().toString();
		String professional = eva_text2.getText().toString();
		String annual_income = eva_text3.getText().toString();
		String fxph = eva_text4.getText().toString();
		String tzph = eva_text5.getText().toString();
		String zczk = eva_text6.getText().toString();
		String jtzk = eva_text7.getText().toString();
		
		if(TextUtils.isEmpty(name)){
			Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
		}else if(TextUtils.isEmpty(professional)){
			Toast.makeText(this, "职业不能为空", Toast.LENGTH_SHORT).show();			
		}else if(TextUtils.isEmpty(annual_income)){
			Toast.makeText(this, "年收入不能为空", Toast.LENGTH_SHORT).show();
		}else{
			editor.putString("text1", name);
			editor.putString("text2", professional);
			editor.putString("text3", annual_income);
			editor.putString("text4", fxph);
			editor.putString("text5", tzph);
			editor.putString("text6", zczk);
			editor.putString("text7", jtzk);
			editor.commit();
			PostEva pe = new PostEva(account.getToken(), name, professional, annual_income, fxph, tzph, zczk, jtzk);
			ThreadExecutor.execute(new PostData(this, mHandler, pe, 5));
			dialogUtil.showProgressDialog(this, "评估中，请稍等...");
		}
	}
}
