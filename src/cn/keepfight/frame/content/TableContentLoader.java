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
 * ������ݼ�������. ������ Frame ���е�����������
 *
 * @author Tom
 *
 */
public class TableContentLoader extends AbstractContentLoader<TableDataSource>{

	/**
	 * �����������ı�����
	 */
	TableView<ObservableList<StringProperty>> table_main;

	/**
	 * Ĭ��ÿҳ��ʾ��¼����������ͨ�����ú���{@link #setPageLimit(int)}}���иı䡣
	 * @TODO ������Ҫ�������ò���
	 */
	private int pageLimit = 10;

	public TableContentLoader(TableView<ObservableList<StringProperty>> table_main) {
		this.table_main = table_main;
	}


	@Override
	public void clear() {
	}

	/**
	 * ����ÿҳ��ʾ����
	 * @param pageLimit �����õ���ʾ��
	 */
	public void setPageLimit(int pageLimit) {
		this.pageLimit = pageLimit;
	}

	/**
	 * ���ñ�����ݼ��ص�����Դ��������֮ǰ����һ������Դ����Ч�ԡ�<br/>
	 * ���Ƿ�����ʹ���Զ�����
	 * @param source �����õ�����Դ
	 * @throws InvalidSourceException ����Դ��Ч�쳣
	 */
	@Override
	public void setDataSource(TableDataSource source) throws InvalidSourceException{
		super.setDataSource(source);
		reload();
	}

	/**
	 * ɾ������Դ����������ݡ�
	 */
	public void clearData() {
		try {
			table_main.getItems().clear();
			table_main.getColumns().clear();
			table_main.setPlaceholder(new Label("�����ݣ�"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * ���ԭ�б�����ݲ��ؼ������ݡ�
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
