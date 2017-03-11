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
 * ������ݼ�������. ������ Frame ���е�����������
 *
 * @author Tom
 *
 */
public class TableContentLoader
		extends AbstractContentLoader<TableDataSource, TableView<ObservableList<StringProperty>>> {

	/**
	 * Ĭ��ÿҳ��ʾ��¼����������ͨ�����ú���{@link #setPageLimit(int)}}���иı䡣
	 *
	 * @TODO ������Ҫ�������ò���
	 */
	private int pageLimit = 10;

	private int pageNow = 1;

	/**
	 * ��ͷ����¼���Ӧ
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
	 * ����ÿҳ��ʾ����
	 *
	 * @param pageLimit
	 *            �����õ���ʾ��
	 */
	public void setPageLimit(int pageLimit) {
		this.pageLimit = pageLimit;
		reload();
	}

	public int getPageLimit() {
		return pageLimit;
	}

	/**
	 * ���ԭ�б�����ݲ��ؼ������ݡ�
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

		// ����Ϊ��������
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

		// ������ͷ��������¼�����
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
