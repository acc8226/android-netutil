package cn.likai.util.http;

import java.io.File;

public abstract class FileCallback extends AbstractCallback<String, String> {

	public FileCallback(String filePath) {
		super.mPath = filePath;

		// 避免上层目录不存在导致的FileNot
		if (filePath != null && filePath.length() > 3) {
			int lastIndexOf = filePath.lastIndexOf(File.separatorChar);
			if (lastIndexOf != -1) {
				File dir = new File(filePath.substring(0, lastIndexOf));
				dir.mkdirs();
			}
		}
	}

	/**
	 * 最终存储成功的图片路径
	 */
	@Override
	protected final String bindData(String filePath) {
		return filePath;
	}

	/**
	 * 先慢后快的方式更迎合人们的需求, 故采用Power函数
	 * 
	 * @param currentLen
	 * @param totalLen
	 * @return 完成百分比
	 */
	public static int power(int currentLen, int totalLen) {
		double x = 1.0D * currentLen / totalLen;
		return power(x);
	}

	/**
	 * 先慢后快的方式更迎合人们的需求, 故采用Power函数
	 * 
	 * @param x
	 * @return 完成百分比
	 */
	public static int power(double x) {
		return (int) (Math.pow((x + (1 - x) * 0.03), 2) * 100);
	}
}
