package cn.keepfight.frame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.keepfight.frame.content.source.InvalidSourceException;
import cn.keepfight.frame.table.TableDataSource;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 表格数据源样本
 * @author Tom
 *
 */
public class SampleTableDataSource extends TableDataSource {

	ObservableList<StringProperty> headList = FXCollections.observableArrayList();;
	List<ObservableList<StringProperty>> dataList = FXCollections.observableArrayList();;
	String urlSpec = "D:/iie_learning/testfile/goods_sale.csv";
	public SampleTableDataSource() {
		try {
			loadSampleData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getSourceIDName() {
		return urlSpec;
	}

	@Override
	public void checkValid() throws InvalidSourceException {
	}

	@Override
	public int getColumnNum() {
		return headList.size();
	}

	@Override
	public int getRowNum() {
		return dataList.size();
	}

	@Override
	public boolean hasHead() {
		return true;
	}

	@Override
	public ObservableList<StringProperty> getHeadList() {
		return headList;
	}

	@Override
	public List<ObservableList<StringProperty>> getRowList(int startRow, int limit) {
		List<ObservableList<StringProperty>> res = new ArrayList<ObservableList<StringProperty>>();
		for (int i = startRow; i < startRow+limit; i++) {
			res.add(dataList.get(i-1));
		}
		return res;
	}


	public void loadSampleData() throws Exception {
		File sinputFile = new File(urlSpec);
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(sinputFile)));

		final String headerLine = in.readLine();
		final String[] headerValues = headerLine.split(",");
		for (int column = 0; column < headerValues.length; column++) {
			headList.add(new SimpleStringProperty(headerValues[column]));
		}
		// Data:
		String dataLine;
		while ((dataLine = in.readLine()) != null) {
			final String[] dataValues = dataLine.split(",");
			// Add data to table:
			ObservableList<StringProperty> data = FXCollections.observableArrayList();
			for (String value : dataValues) {
				data.add(new SimpleStringProperty(value));
			}
			dataList.add(data);
		}
		in.close();
	}

	@Override
	public String getDB() {
		return "";
	}

	@Override
	public String getEntityName() {
		return "";
	}


//	public void loadSampleData() throws Exception {
//		String urlSpec = "D:/iie_learning/testfile/goods_sale.csv";
//		File sinputFile = new File(urlSpec);
//		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(sinputFile)));
//
//		final String headerLine = in.readLine();
//		final String[] headerValues = headerLine.split(",");
//		for (int column = 0; column < headerValues.length; column++) {
//			table_main.getColumns().add(createColumn(column, headerValues[column]));
//		}
//		// Data:
//		String dataLine;
//		while ((dataLine = in.readLine()) != null) {
//			final String[] dataValues = dataLine.split(",");
//			for (int columnIndex = table_main.getColumns().size(); columnIndex < dataValues.length; columnIndex++) {
//				table_main.getColumns().add(createColumn(columnIndex, ""));
//			}
//			// Add data to table:
//			ObservableList<StringProperty> data = FXCollections.observableArrayList();
//			for (String value : dataValues) {
//				data.add(new SimpleStringProperty(value));
//			}
//			table_main.getItems().add(data);
//		}
//		in.close();
//	}
//
//	private TableColumn<ObservableList<StringProperty>, String> createColumn(final int columnIndex,
//			String columnTitle) {
//		TableColumn<ObservableList<StringProperty>, String> column = new TableColumn<>();
//		String title;
//		if (columnTitle == null || columnTitle.trim().length() == 0) {
//			title = "Column " + (columnIndex + 1);
//		} else {
//			title = columnTitle;
//		}
//		column.setText(title);
//		column.setCellValueFactory(
//				new Callback<TableColumn.CellDataFeatures<ObservableList<StringProperty>, String>, ObservableValue<String>>() {
//					@Override
//					public ObservableValue<String> call(
//							CellDataFeatures<ObservableList<StringProperty>, String> cellDataFeatures) {
//						ObservableList<StringProperty> values = cellDataFeatures.getValue();
//						if (columnIndex >= values.size()) {
//							return new SimpleStringProperty("");
//						} else {
//							return cellDataFeatures.getValue().get(columnIndex);
//						}
//					}
//				});
//		return column;
//	}
}
