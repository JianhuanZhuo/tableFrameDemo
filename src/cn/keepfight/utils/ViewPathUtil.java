package cn.keepfight.utils;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;

public class ViewPathUtil {

	private static ViewPathUtil instance = new ViewPathUtil();

	/**
	 * 加载器缓存，未实现
	 */
	//private static Map<URL, FXMLLoader> loaders = new HashMap<URL, FXMLLoader>();

	/**
	 * 指定frame视图名，获得对应的URL路径
	 * @param viewURL 视图名
	 * @return URL路径
	 */
	public static URL getFrameView(String viewURL) {
		//@TODO 做出配置的方式
		if (!viewURL.contains(".fxml")) {
			System.out.println(viewURL+" do not contain .fxml! It's may wrong!");
		}
		return instance.getClass().getResource("/cn/keepfight/frame/view/"+viewURL);
	}

	/**
	 * 指定frame视图名，加载视图实例
	 * @param viewURL  视图名
	 * @return 视图实例
	 * @throws IOException IO异常
	 */
	public static Object loadView(String viewURL) throws IOException {
		return getLoader(viewURL).load();
	}

	/**
	 * 指定frame视图名，获得该视图加载器
	 * @param viewURL 视图名
	 * @return 加载器
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
