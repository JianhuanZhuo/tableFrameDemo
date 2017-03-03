package cn.keepfight.frame.content;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import cn.keepfight.frame.content.source.InvalidSourceException;
import cn.keepfight.frame.content.source.TableDataSource;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * 表格内容加载器类. 单个表 Frame 会有单个加载器，
 *
 * @author Tom
 *
 */
public class TableContentLoader extends AbstractContentLoader<TableDataSource>{

	/**
	 * 加载器操作的表格对象。
	 */
	TableView<ObservableList<StringProperty>> table_main;

	/**
	 * 默认每页显示纪录行数，可以通过调用函数{@link #setPageLimit(int)}}进行改变。
	 * @TODO 这里需要做成配置参数
	 */
	private int pageLimit = 10;

	public TableContentLoader(TableView<ObservableList<StringProperty>> table_main) {
		this.table_main = table_main;
	}


	@Override
	public void clear() {
	}

	/**
	 * 设置每页显示条数
	 * @param pageLimit 欲设置的显示数
	 */
	public void setPageLimit(int pageLimit) {
		this.pageLimit = pageLimit;
	}

	/**
	 * 设置表格数据加载的数据源，在设置之前会检查一次数据源的有效性。<br/>
	 * 覆盖方法，使其自动加载
	 * @param source 欲设置的数据源
	 * @throws InvalidSourceException 数据源无效异常
	 */
	@Override
	public void setDataSource(TableDataSource source) throws InvalidSourceException{
		super.setDataSource(source);
		reload();
	}

	/**
	 * 删除数据源，并清空数据。
	 */
	public void clearData() {
		try {
			table_main.getItems().clear();
			table_main.getColumns().clear();
			table_main.setPlaceholder(new Label("无数据！"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 清空原有表格数据并重加载数据。
	 */
	public void reload() {
		clearData();
	}

	/**
	 *
	 * @param columnTitle
	 */
	private void addColumn(String columnTitle) {

	}

	private void appendRow(List<String> row) {

	}

	public void loadSampleData() throws Exception {
		clearData();
		String urlSpec = "D:/iie_learning/testfile/goods_sale.csv";
		File sinputFile = new File(urlSpec);
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(sinputFile)));

		final String headerLine = in.readLine();
		final String[] headerValues = headerLine.split(",");
		for (int column = 0; column < headerValues.length; column++) {
			table_main.getColumns().add(createColumn(column, headerValues[column]));
		}
		// Data:
		String dataLine;
		while ((dataLine = in.readLine()) != null) {
			final String[] dataValues = dataLine.split(",");
			for (int columnIndex = table_main.getColumns().size(); columnIndex < dataValues.length; columnIndex++) {
				table_main.getColumns().add(createColumn(columnIndex, ""));
			}
			// Add data to table:
			ObservableList<StringProperty> data = FXCollections.observableArrayList();
			for (String value : dataValues) {
				data.add(new SimpleStringProperty(value));
			}
			table_main.getItems().add(data);
		}
		in.close();
	}

	private TableColumn<ObservableList<StringProperty>, String> createColumn(final int columnIndex,
			String columnTitle) {
		TableColumn<ObservableList<StringProperty>, String> column = new TableColumn<>();
		String title;
		if (columnTitle == null || columnTitle.trim().length() == 0) {
			title = "Column " + (columnIndex + 1);
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
		return column;
	}
}
