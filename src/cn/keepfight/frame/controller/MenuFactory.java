package cn.keepfight.frame.controller;

import java.io.IOException;

import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.utils.ViewPathUtil;
import javafx.fxml.FXMLLoader;

/**
 * 菜单视图控制器工厂类
 * @author Tom
 *
 */
public class MenuFactory {

	/**
	 * 工厂方法，根据参数类型进行构造。
	 * @return 菜单视图控制器对象
	 */
	public static MenuViewController generateMenuC(DataSourceType type) {
		switch (type) {
		case TABLE:
			return new TableMenuViewController();
		default:
			break;
		}
		return null;
	}

	/**
	 * 使用数据源类型生成视图和控制器，并返回控制器。视图可从控制器中访问
	 * @param type 数据源类型
	 * @return 控制器
	 */
	public static MenuViewController generateMenuVC(DataSourceType type) {
		FXMLLoader loader = new FXMLLoader(ViewPathUtil.getFrameView(type.getMenuViewPool().getPriprorViewURL()));
		MenuViewController res = generateMenuC(type);
		loader.setController(res);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			res = null;
		}
		return res;
	}
}
