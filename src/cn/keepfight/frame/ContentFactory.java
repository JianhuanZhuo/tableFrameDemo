package cn.keepfight.frame;

import java.io.IOException;

import cn.keepfight.frame.chain.ChainPaneController;
import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.files.FilesPaneController;
import cn.keepfight.frame.operator.OperatorPaneController;
import cn.keepfight.frame.picture.PicturePaneController;
import cn.keepfight.frame.table.TablePaneController;
import cn.keepfight.frame.text.TextPaneController;
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
		case CHAIN:
			return new ChainPaneController();
		case OPERATOR:
			return new OperatorPaneController();
		case TEXT:
			return new TextPaneController();
		case FILES:
			return new FilesPaneController();
		case PICTURE:
			return new PicturePaneController();
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
