package cn.keepfight.frame.table;

import cn.keepfight.frame.PaneController;
import cn.keepfight.frame.TStage;
import cn.keepfight.frame.TableTStage;
import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;

/**
 * 表格面板控制器.
 *
 * @author Tom
 *
 */
public class TablePaneController extends PaneController{

	TableDataSource source;

	private TableTStage tStage;

	public SelectOperatorUtil columnSelectUtil;

	TableSelect tableSelect = new TableSelect();

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

	public TableContentLoader getLoader() {
		return loader;
	}

	@Override
	public BorderPane getNode() {
		return pane;
	}

	@Override
	public void clearContent() {
	}

	EventHandler<? super MouseEvent> handler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			int select = (int) ((Node)event.getSource()).getProperties().get("column_object");
			String title = (String) ((Node)event.getSource()).getProperties().get("column_name");
			if (event.isControlDown()) {
				tableSelect.addColumnSelect(new Pair<Integer, String>(select, title), table.getColumns().get(select));
			}else {
				tableSelect.columnSelect(new Pair<Integer, String>(select, title), table.getColumns().get(select));
			}
		}
	};

	@FXML
	public void initialize() {

		// 单元选择
		table.getSelectionModel().setCellSelectionEnabled(true);
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		pagedown.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				loader.loadNextPage();
				updatePaneField();
			}
		});

		pageup.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				loader.loadPrePage();
				updatePaneField();
			}
		});

		numPerPageField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					loader.setPageLimit(Integer.valueOf(numPerPageField.getText()));
					updatePaneField();
				}
			}
		});

		numPerPageField.focusedProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					loader.setPageLimit(Integer.valueOf(numPerPageField.getText()));
					updatePaneField();
				}
			}
		});


		toPageField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					loader.setPageNow(Integer.valueOf(toPageField.getText()));
					updatePaneField();
				}
			}
		});

		toPageField.focusedProperty().addListener(new ChangeListener<Boolean>(){
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					loader.setPageNow(Integer.valueOf(toPageField.getText()));
					updatePaneField();
				}
			}
		});
	}

	@Override
	public void setDataSource(DataSource source) throws InvalidSourceException {
		if (source instanceof TableDataSource) {
			if (loader==null) {
				loader = new TableContentLoader((TableDataSource)source, table);
			}
			loader.setDataSource((TableDataSource)source);
			loader.setHandler(handler);
			updatePaneField();
		}
	}


	@Override
	@SuppressWarnings("rawtypes")
	public void setTStage(TStage tStage) {
		this.tStage = (TableTStage) tStage;
		columnSelectUtil = new SelectOperatorUtil((TableTStage) tStage);
	}

	@Override
	public void load() {
		loader.reload();
	}

	private void updatePaneField() {
		toPageField.setText(""+loader.getPageNow());
		numPerPageField.setText(""+loader.getPageLimit());
		totalPages.setText("/"+loader.getPageTotalNum()+"页");
	}

	public TableSelect getTableSelect() {
		return tableSelect;
	}
}
