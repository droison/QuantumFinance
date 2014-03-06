package com.QuantumFinance.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageUtil {

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth) {

		final int width = options.outWidth;
		int inSampleSize = 1;

		if (width > reqWidth) {
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = widthRatio;
		}
		return inSampleSize;
	}

	// 根据路径获得图片并压缩，返回bitmap用于显示
	public static Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 500);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	public static void bitmapCompress(String filePath) {

		Bitmap bm = getSmallBitmap(filePath);
		OutputStream ous = null;
		try {
			ous = new FileOutputStream(new File(filePath));
			bm.compress(Bitmap.CompressFormat.JPEG, 80, ous);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (ous != null) {
				try {
					ous.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void bitmapCompress(String inputPath, String output) {

		Bitmap bm = getSmallBitmap(inputPath);
		OutputStream ous = null;
		try {
			ous = new FileOutputStream(new File(output));
			bm.compress(Bitmap.CompressFormat.JPEG, 80, ous);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (ous != null) {
				try {
					ous.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void bitMapCut(String inputPath, String output, float hw) {

		Bitmap bm = BitmapFactory.decodeFile(inputPath);
		if (bm != null) {

			float w = bm.getWidth();
			float h = bm.getHeight();
			float temp = h / w;
			if (temp > hw) {
				int htemp = (int) (w * hw);
				bm = Bitmap.createBitmap(bm, 0, (int) ((h - htemp) / 2), (int) w, htemp);

			} else if (temp < hw) {
				int wtemp = (int) (h / hw);
				bm = Bitmap.createBitmap(bm, (int) ((w - wtemp) / 2), 0, wtemp, (int) h);
			}

			OutputStream ous = null;
			try {
				ous = new FileOutputStream(new File(output));
				bm.compress(Bitmap.CompressFormat.JPEG, 80, ous);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (ous != null) {
					try {
						ous.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

	}


	public static void copyFile(String input, String output) throws Exception {
		FileInputStream fi = new FileInputStream(input);
		BufferedInputStream in = new BufferedInputStream(fi);
		FileOutputStream fo = new FileOutputStream(output);
		BufferedOutputStream out = new BufferedOutputStream(fo);
		byte[] buf = new byte[1024];
		int len = in.read(buf);// 读文件，将读到的内容放入到buf数组中，返回的是读到的长度
		while (len != -1) {
			out.write(buf, 0, len);
			len = in.read(buf);
		}
		out.close();
		fo.close();
		in.close();
		fi.close();
	}

	public static Bitmap readBitmapAutoSize(String filePath, int outWidth, int outHeight) {
		// outWidth和outHeight是目标图片的最大宽度和高度，用作限制
		FileInputStream fs = null;
		BufferedInputStream bs = null;
		try {
			fs = new FileInputStream(filePath);
			bs = new BufferedInputStream(fs);
			BitmapFactory.Options options = setBitmapOption(filePath, outWidth, outHeight);
			return BitmapFactory.decodeStream(bs, null, options);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bs.close();
				fs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static BitmapFactory.Options setBitmapOption(String file, int width, int height) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		// 设置只是解码图片的边距，此操作目的是度量图片的实际宽度和高度
		BitmapFactory.decodeFile(file, opt);

		int outWidth = opt.outWidth; // 获得图片的实际高和宽
		int outHeight = opt.outHeight;
		opt.inDither = false;
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		// 设置加载图片的颜色数为16bit，默认是RGB_8888，表示24bit颜色和透明通道，但一般用不上
		opt.inSampleSize = MemoryUtil.bitMapSize();
		// 设置缩放比,1表示原比例，2表示原来的四分之一....
		// 计算缩放比
		if (outWidth != 0 && outHeight != 0 && width != 0 && height != 0) {
			int sampleSize = (outWidth / width + outHeight / height) / 2;
			opt.inSampleSize = sampleSize;
		}

		opt.inJustDecodeBounds = false;// 最后把标志复原
		return opt;
	}

}
