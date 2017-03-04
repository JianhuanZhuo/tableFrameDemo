package cn.keepfight.frame.controller;

import cn.keepfight.frame.content.TableContentLoader;
import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
import cn.keepfight.frame.content.source.TableDataSource;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * 表格面板控制器.
 *
 * @author Tom
 *
 */
public class TablePaneController extends PaneController{

	TableDataSource source;

	/**
	 * 画板的根布局
	 */
	@FXML
	BorderPane pane;

	@FXML
	TableView<ObservableList<StringProperty>> table;

	/**
	 * 跳转指定页输入框
	 */
	@FXML
	TextField toPageField;

	/**
	 * 记录数每页输入框
	 */
	@FXML
	TextField numPerPageField;

	/**
	 * 页总数标签，内容应该类似为"/30页"的文本
	 */
	@FXML
	Label totalPages;

	@FXML
	Button pageup;

	@FXML
	Button pagedown;


	TableContentLoader loader;

	@Override
	public BorderPane getNode() {
		return pane;
	}

	@Override
	public void clearContent() {
	}

	@FXML
	public void initialize() {
	}

	@Override
	public void setDataSource(DataSource source) throws InvalidSourceException {
		if (source instanceof TableDataSource) {
			if (loader==null) {
				loader = new TableContentLoader((TableDataSource)source, table);
			}
			loader.setDataSource((TableDataSource)source);
		}
	}

	@Override
	public void load() {
		loader.reload();
	}
}
