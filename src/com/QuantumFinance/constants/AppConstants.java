package com.QuantumFinance.constants;

import android.os.Environment;

public class AppConstants {

	public static final String APP_FILE_NAME = "LzFinance";
	public static final String APP_FILE_PATH = Environment.getExternalStorageDirectory() + "/" + APP_FILE_NAME;
	
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
	
	/**
	 * URL
	 */
	public interface HTTPURL{
		public static final String checkVersion = "http://code.taobao.org/svn/versioncontrol/trunk/lzversion";
		
		public static final String serverIP = "http://115.28.229.188";
	}
	
}
