package com.QuantumFinance.constants;

import android.os.Environment;

public class AppConstants {
	public static final String TEXT_HTML = "text/html";

	public static final String UTF8 = "utf-8";

	public static final String CSS = "<head><style type=\"text/css\">body {max-width: 100%}\nimg {max-width: 100%; height: auto;}\ndiv[style] {max-width: 100%;}\npre {white-space: pre-wrap;}</style></head>";
	public static final String FONT_START = CSS + "<body link=\"#97ACE5\" text=\"#666666\">";
	public static final String BODY_START = "<body>";

	public static final String BODY_END = "</body>";

	public static final String APP_FILE_NAME = "LzFinance";
	public static final String APP_FILE_PATH = Environment.getExternalStorageDirectory() + "/" + APP_FILE_NAME;
	public static final String TEMP_HEAD_FILE_PATH = APP_FILE_PATH + "/head.jpg";

	/**
	 * 网络访问相关的常量,以200作为头，希望HTTP code永远200！
	 */
	public static final int HANDLER_MESSAGE_NORMAL = 0X200001;
	public static final int HANDLER_MESSAGE_NULL = 0X200002;
	public static final int HANDLER_HTTPSTATUS_ERROR = 0X200003;
	public static final int HANDLER_MESSAGE_NONETWORK = 0X200004;

	public static final int HANDLER_APK_DOWNLOAD_FINISH = 0X30001;
	public static final int HANDLER_APK_DOWNLOAD_PROGRESS = 0X30002;
	public static final int HANDLER_APK_STOP = 0X30003;
	public static final int HANDLER_VERSION_UPDATE = 0X30004;
	public static final int databaseversion = 3;

	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
	}

	/**
	 * URL
	 */
	public interface HTTPURL {
		public static final String checkVersion = "http://code.taobao.org/svn/versioncontrol/trunk/lzversion";

		public static final String serverIP = "http://42.121.104.18:8089";
		/**
		 * 第一页
		 */
		public static final String wenjian = serverIP + "/terminal_interface/loan_informations/promoted_products.json?type=solid&page=1&per_page=10"; // GET地址写死了
		public static final String jijin = serverIP + "/terminal_interface/loan_informations/promoted_products.json?type=radical&page=1&per_page=10"; // GET地址写死了
		public static final String recommendInfo = serverIP + "/terminal_interface/loan_informations/show_product.json?id=";// 后面跟着ID
		public static final String ppt = serverIP + "/terminal_interface/ppts/get_ppt.json?per_page=10&page=1"; // 后面跟着页数
		/**
		 * 第二页
		 */
		public static final String paperlist = serverIP + "/terminal_interface/comments/expert_comments_list.json?per_page=10&page=";// GET
																																		// 后面跟页数
		public static final String paperInfo = serverIP + "/terminal_interface/comments/expert_comments_show.json?id=";// GET后面跟着ID
		public static final String commentlist = serverIP + "/terminal_interface/user_comments/user_comment_list.json?per_page=10&comment_id="; // 跟着commentid和page
		public static final String commentpost = serverIP + "/terminal_interface/user_comments/user_comment.json";// POST
																											// user_id
																											// comment_id
																											// content
		public static final String praise = serverIP + "/terminal_interface/comments/praise.json?id=";// 后面跟文章ID

		/**
		 * 第三页
		 */
		public static final String history = serverIP + "/terminal_interface/loan_informations/products_histroy.json?per_page=10&page=";// 后面跟着页码

		/**
		 * 第四页
		 */
		public static final String reg = serverIP + "/users.json"; // POST
																	// user[email]
																	// user[password]
																	// user[password_confirmation]
		public static final String login = serverIP + "/terminal_interface/sessions/login.json";// POST
																								// email
																								// password
		public static final String lostPwd = serverIP + "/users/password.json";// POST
																				// user[email]
	}

}
