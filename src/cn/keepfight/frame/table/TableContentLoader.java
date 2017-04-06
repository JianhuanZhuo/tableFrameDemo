package cn.keepfight.frame.table;

import java.util.ArrayList;
import java.util.List;

import cn.keepfight.frame.content.source.InvalidSourceException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * 表格内容加载器类. 单个表 Frame 会有单个加载器，
 *
 * @author Tom
 *
 */
public class TableContentLoader
		extends AbstractContentLoader<TableDataSource, TableView<ObservableList<StringProperty>>> {

	/**
	 * 默认每页显示纪录行数，可以通过调用函数{@link #setPageLimit(int)}}进行改变。
	 *
	 * @TODO 这里需要做成配置参数
	 */
	private int pageLimit = 10;

	private int pageNow = 1;

	/**
	 * 列头点击事件响应
	 */
	private EventHandler<? super MouseEvent> handler;

	public void setHandler(EventHandler<? super MouseEvent> handler) {
		this.handler = handler;
	}

	public TableContentLoader(TableDataSource source, TableView<ObservableList<StringProperty>> node)
			throws InvalidSourceException {
		super(source, node);
	}

	@Override
	public void clear() {
		if (node != null) {
			node.getItems().clear();
			node.getColumns().clear();
		}
	}

	/**
	 * 设置每页显示条数
	 *
	 * @param pageLimit
	 *            欲设置的显示数
	 */
	public void setPageLimit(int pageLimit) {
		this.pageLimit = pageLimit;
		reload();
	}

	public int getPageLimit() {
		return pageLimit;
	}

	/**
	 * 清空原有表格数据并重加载数据。
	 */
	@Override
	public void reload() {
		clear();
		for (StringProperty p : source.getHeadList()) {
			addColumn(p.getValue());
		}
		List<ObservableList<StringProperty>> dataList = source.getRowList((pageNow - 1) * pageLimit, pageLimit);
		for (ObservableList<StringProperty> observableList : dataList) {
			appendRow(observableList);
		}
		int cols = node.getColumns().size();
		for (int i = 0; i < cols; i++) {
			node.getColumns().get(i).prefWidthProperty().bind(node.widthProperty().divide(cols));
		}
	}

	/**
	 *
	 * @param columnTitle
	 */
	private TableColumn<ObservableList<StringProperty>, String> addColumn(String columnTitle) {
		TableColumn<ObservableList<StringProperty>, String> column = new TableColumn<>();
		String title;
		final int columnIndex = node.getColumns().size();
		if (columnTitle == null || columnTitle.trim().length() == 0) {
			title = "Column " + columnIndex;
		} else {
			title = columnTitle;
		}

		// 设置为不可排序
		column.setSortable(false);

		column.setText(title);
		column.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<ObservableList<StringProperty>, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<ObservableList<StringProperty>, String> cellDataFeatures) {
						ObservableList<StringProperty> values = cellDataFeatures.getValue();
						if (columnIndex >= values.size()) {
							return new SimpleStringProperty("");
						} else {
							return cellDataFeatures.getValue().get(columnIndex);
						}
					}
				});
		node.getColumns().add(column);

		// 更换表头，并添加事件监听
		VBox vBox = new VBox(new Label(columnTitle));
		vBox.setAlignment(Pos.CENTER);
		vBox.getProperties().put("column_object", columnIndex);
		vBox.getProperties().put("column_name", columnTitle);
		column.setGraphic(vBox);
		column.setText("");
		column.getGraphic().addEventFilter(MouseEvent.MOUSE_CLICKED, handler);

		column.setPrefWidth(Control.USE_COMPUTED_SIZE);
		return column;
	}

	private void appendRow(ObservableList<StringProperty> row) {
		node.getItems().add(row);
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		if (pageNow <= 0 || pageNow > getPageTotalNum()) {
			return;
		}
		this.pageNow = pageNow;
		reload();
	}

	public int getPageTotalNum() {
		return (source.getRowNum() / pageLimit) + 1;
	}

	public boolean loadNextPage() {
		if (pageNow >= getPageTotalNum()) {
			return false;
		}
		pageNow = pageNow + 1;
		reload();
		return true;
	}

	public boolean loadPrePage() {
		if (pageNow <= 1) {
			return false;
		}
		pageNow = pageNow - 1;
		reload();
		return true;
	}

	public List<String> allColumns() {
		List<String> res = new ArrayList<String>();
		source.getHeadList()
				.stream()
				.map(p -> p.getValue())
				.forEach(s -> res.add(s));
		return res;
	}
}
