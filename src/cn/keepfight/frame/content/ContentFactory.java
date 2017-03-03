package cn.keepfight.frame.content;

import java.io.IOException;

import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.controller.ChainPaneController;
import cn.keepfight.frame.controller.PaneController;
import cn.keepfight.frame.controller.TablePaneController;
import cn.keepfight.utils.ViewPathUtil;
import javafx.fxml.FXMLLoader;

public class ContentFactory {

	/**
	 * 工厂方法，根据参数类型进行构造。
	 * @return 菜单视图控制器对象
	 */
	public static PaneController generateContentC(DataSourceType type) {
		switch (type) {
		case TABLE:
			return new TablePaneController();
		case OPERATORCHAIN:
			return new ChainPaneController();
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
	public static PaneController generateContentVC(DataSourceType type) {
		FXMLLoader loader = new FXMLLoader(ViewPathUtil.getFrameView(type.getContentViewPool().getPriprorViewURL()));
		PaneController res = generateContentC(type);
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
