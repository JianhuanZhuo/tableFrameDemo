package cn.keepfight.frame.content;

import java.util.List;

import cn.keepfight.frame.content.source.InvalidSourceException;
import cn.keepfight.frame.content.source.TableDataSource;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
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
		List<ObservableList<StringProperty>> dataList = source.getRowList(1, pageLimit);
		for (ObservableList<StringProperty> observableList : dataList) {
			appendRow(observableList);
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
		return column;

	}

	private void appendRow(ObservableList<StringProperty> row) {
		node.getItems().add(row);
	}

}
