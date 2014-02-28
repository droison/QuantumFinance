package com.QuantumFinance.ui;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.graphics.Color;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {
	/*
	 * private static final String NEWLINE = "\n";
	 * 
	 * private static final String BR = "<br/>";
	 */

	private static final String TEXT_HTML = "text/html";

	private static final String UTF8 = "utf-8";

	private static final String CSS = "<head><style type=\"text/css\">body {max-width: 100%}\nimg {max-width: 100%; height: auto;}\ndiv[style] {max-width: 100%;}\npre {white-space: pre-wrap;}</style></head>";

	private static final String FONT_START = CSS + "<body link=\"#97ACE5\" text=\"#C0C0C0\">";

	private static final String FONT_FONTSIZE_START = CSS + "<body link=\"#97ACE5\" text=\"#C0C0C0\"><font size=\"+";

	private static final String FONTSIZE_START = "<font size=\"+";

	private static final String FONTSIZE_MIDDLE = "\">";

	private static final String FONTSIZE_END = "</font>";

	private static final String FONT_END = "</font><br/><br/><br/><br/></body>";

	private static final String BODY_START = "<body>";

	private static final String BODY_END = "<br/><br/><br/><br/></body>";

	private static final int BUTTON_ALPHA = 180;

	private static final String IMAGE_ENCLOSURE = "[@]image/";

	private static final String TEXTPLAIN = "text/plain";

	private static final String BRACKET = " (";

	private WebView webView;

	private WebView webView0; // only needed for the animation

	private ViewFlipper viewFlipper;

	int scrollX;

	int scrollY;

	private LayoutParams layoutParams;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		viewFlipper = (ViewFlipper) findViewById(R.id.content_flipper);

		layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

		webView = new WebView(this);

		viewFlipper.addView(webView, layoutParams);

		OnKeyListener onKeyEventListener = new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == 92 || keyCode == 94) {
						scrollUp();
						return true;
					} else if (keyCode == 93 || keyCode == 95) {
						scrollDown();
						return true;
					}
				}
				return false;
			}
		};
		webView.setOnKeyListener(onKeyEventListener);

		webView0 = new WebView(this);
		webView0.setOnKeyListener(onKeyEventListener);

		final boolean gestures = true;

		final GestureDetector gestureDetector = new GestureDetector(this, new OnGestureListener() {
			public boolean onDown(MotionEvent e) {
				return false;
			}

			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
				if (gestures) {
					if (Math.abs(velocityY) < Math.abs(velocityX)) {
						if (velocityX > 800) {
							Toast.makeText(MainActivity.this, "last", 1).show();
						} else if (velocityX < -800) {
							Toast.makeText(MainActivity.this, "next", 1).show();
						}
					}
				}
				return false;
			}

			public void onLongPress(MotionEvent e) {

			}

			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
				return false;
			}

			public void onShowPress(MotionEvent e) {

			}

			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}
		});

		OnTouchListener onTouchListener = new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		};

		webView.setOnTouchListener(onTouchListener);

		webView0.setOnTouchListener(onTouchListener);

		scrollX = 0;
		scrollY = 0;
		loadThread.start();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		webView.restoreState(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	Handler loadHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String result = (String) msg.obj;
			webView.loadDataWithBaseURL(null, new StringBuilder(CSS).append(BODY_START).append(result).append(BODY_END).toString(), TEXT_HTML, UTF8, null);
			webView.setBackgroundColor(Color.WHITE);
		};
	};

	Thread loadThread = new Thread() {
		public void run() {

			try {
				URL url;
				url = new URL("http://192.168.1.102/content");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.connect();
				InputStream is = conn.getInputStream();
				String result = readInputStream(is);
				is.close();
				Message msg = new Message();
				msg.obj = result;
				loadHandler.sendMessage(msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		};
	};

	public String readInputStream(InputStream inStream) throws Exception {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = inStream.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	@Override
	protected void onPause() {
		super.onPause();
		scrollX = webView.getScrollX();
		scrollY = webView.getScrollY();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (keyCode == 92 || keyCode == 94) {
				scrollUp();
				return true;
			} else if (keyCode == 93 || keyCode == 95) {
				scrollDown();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private void scrollUp() {
		if (webView != null) {
			webView.pageUp(false);
		}
	}

	private void scrollDown() {
		if (webView != null) {
			webView.pageDown(false);
		}
	}

}
