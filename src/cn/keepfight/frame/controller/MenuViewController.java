package cn.keepfight.frame.controller;

import java.io.IOException;

import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.menu.MenuItemType;
import cn.keepfight.utils.ViewPathUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * 菜单视图控制器，<br/>
 * 由于它是抽象类，所以无法直接作为
 * @author Tom
 *
 * @param <T> T参数表示菜单视图状态，需要在其他子菜单视图控制器中得到具体实现
 */
public abstract class MenuViewController{

	@FXML
	TabPane tabPane;

	/**
	 * 为算子添加到指定索引的菜单组中
	 * 重载方法默认使用 {@link cn.keepfight.frame.menu.MenuItemType.TP_32_TOP} 作为图标类型。
	 * @param operatorModel 欲添加菜单项的算子。
	 * @param groupIndex 组索引，由0开始计数，最大不超过当前可用组
	 * @param type 指定创建菜单项所使用的视图模板
	 * @return 菜单项对应的控制器，出错返回null
	 */
	public MenuItemController addMenuItem(OperatorResource operatorModel, int groupIndex, MenuItemType type) {
		if (operatorModel==null || groupIndex<0 || groupIndex >= tabPane.getTabs().size()) {
			return null;
		}

		FXMLLoader loader = ViewPathUtil.getLoader(type.getViewURL());
		try {
			GridPane gridPane = loader.load();
			HBox hBox = (HBox)tabPane.getTabs().get(groupIndex).getContent();
			hBox.getChildren().add(gridPane);

			MenuItemController controller = loader.getController();
			return controller;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}


	public TabPane getNode() {
		return tabPane;
	}
}
