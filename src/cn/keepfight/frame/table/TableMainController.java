package cn.keepfight.frame.table;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class TableMainController{

	@FXML
	TableView<ObservableList<StringProperty>> table_main;

	public TableMainController() {
    }

	@FXML
	public void initialize() {
		try {
			//new TableContentLoader(table_main).loadSampleData();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
