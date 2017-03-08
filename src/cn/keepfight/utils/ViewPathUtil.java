package cn.keepfight.utils;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;

public class ViewPathUtil {

	private static ViewPathUtil instance = new ViewPathUtil();

	/**
	 * ���������棬δʵ��
	 */
	//private static Map<URL, FXMLLoader> loaders = new HashMap<URL, FXMLLoader>();

	/**
	 * ָ��frame��ͼ������ö�Ӧ��URL·��
	 * @param viewURL ��ͼ��
	 * @return URL·��
	 */
	public static URL getFrameView(String viewURL) {
		//@TODO �������õķ�ʽ
		if (!viewURL.contains(".fxml")) {
			System.out.println(viewURL+" do not contain .fxml! It's may wrong!");
		}
		return instance.getClass().getResource("/cn/keepfight/frame/view/"+viewURL);
	}

	/**
	 * ָ��frame��ͼ����������ͼʵ��
	 * @param viewURL  ��ͼ��
	 * @return ��ͼʵ��
	 * @throws IOException IO�쳣
	 */
	public static Object loadView(String viewURL) throws IOException {
		return getLoader(viewURL).load();
	}

	/**
	 * ָ��frame��ͼ������ø���ͼ������
	 * @param viewURL ��ͼ��
	 * @return ������
	 */
	public static FXMLLoader getLoader(String viewURL) {
		return new FXMLLoader(ViewPathUtil.getFrameView(viewURL));
	}

	public static Object loadViewForController(String viewURL) throws IOException {
		FXMLLoader loader = getLoader(viewURL);
		loader.load();
		return loader.getController();
	}
}
