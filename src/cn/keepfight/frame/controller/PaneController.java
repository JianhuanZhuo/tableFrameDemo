package cn.keepfight.frame.controller;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

/**
 * 面板控制器抽象类.
 * @author Tom
 *
 */
public abstract class PaneController {

	/**
	 * 画板的根布局
	 */
	@FXML
	BorderPane pane;

	/**
	 * 清空画板内容
	 */
	public abstract void clearContent();

	public BorderPane getNode() {
		return pane;
	}

	/**
	 * 指示画板开始加载画面或数据
	 */
	public abstract void load();

	/**
	 * 为画板设置数据源
	 * @param source 数据源
	 */
	public abstract void setDataSource(DataSource source) throws InvalidSourceException;
}
