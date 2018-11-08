package cn.likai.util.http;

import java.io.File;

public abstract class FileCallback extends AbstractCallback<String, String> {

	public FileCallback(String filePath) {
		super.mPath = filePath;

		// �����ϲ�Ŀ¼�����ڵ��µ�FileNot
		if (filePath != null && filePath.length() > 3) {
			int lastIndexOf = filePath.lastIndexOf(File.separatorChar);
			if (lastIndexOf != -1) {
				File dir = new File(filePath.substring(0, lastIndexOf));
				dir.mkdirs();
			}
		}
	}

	/**
	 * ���մ洢�ɹ���ͼƬ·��
	 */
	@Override
	protected final String bindData(String filePath) {
		return filePath;
	}

	/**
	 * �������ķ�ʽ��ӭ�����ǵ�����, �ʲ���Power����
	 * 
	 * @param currentLen
	 * @param totalLen
	 * @return ��ɰٷֱ�
	 */
	public static int power(int currentLen, int totalLen) {
		double x = 1.0D * currentLen / totalLen;
		return power(x);
	}

	/**
	 * �������ķ�ʽ��ӭ�����ǵ�����, �ʲ���Power����
	 * 
	 * @param x
	 * @return ��ɰٷֱ�
	 */
	public static int power(double x) {
		return (int) (Math.pow((x + (1 - x) * 0.03), 2) * 100);
	}
}
