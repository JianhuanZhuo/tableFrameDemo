package cn.keepfight.frame.table;

import java.util.ArrayList;
import java.util.List;

import cn.keepfight.frame.content.source.InvalidSourceException;
import cn.keepfight.utils.HttpUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DBTableDataSource extends TableDataSource {

	String db;
	String table;
	List<String> fieldList;
	int rowNum = -1;

	public DBTableDataSource(String db, String table) {
		this.db = db;
		this.table = table;
		String url = "http://127.0.0.1:8080/dap/dataLoad/getCloum.htm?db="+db+"&tableName="+table;
		String res;
		try {
			res = HttpUtils.simpleGet(url);
			System.out.println(res.length());
			JSONObject resx = JSONObject.fromObject(res);
			System.out.println(resx.getBoolean("flag"));
			JSONArray fields = resx.getJSONArray("fields");
			fieldList = new ArrayList<String>();
			for (int i = 0; i < fields.size(); i++) {
				fieldList.add(fields.getString(i));
			}
			rowNum = resx.getInt("rowNum");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getSourceIDName() {
		return db+"\\"+table;
	}

	@Override
	public void checkValid() throws InvalidSourceException {
	}

	@Override
	public int getColumnNum() {
		return fieldList.size();
	}

	@Override
	public int getRowNum() {
		return rowNum;
	}

	@Override
	public boolean hasHead() {
		return !fieldList.isEmpty();
	}

	@Override
	public ObservableList<StringProperty> getHeadList() {
		ObservableList<StringProperty> headList = FXCollections.observableArrayList();
		for (String s : fieldList) {
			headList.add(new SimpleStringProperty(s));
		}
		return headList;
	}

	@Override
	public List<ObservableList<StringProperty>> getRowList(int startRow, int limit) {
		List<ObservableList<StringProperty>> rowList = new ArrayList<>();
		try {
			String url = "http://127.0.0.1:8080/dap/dataLoad/getTableContent.htm?table="+table+"&start="+startRow+"&limit="+limit;
			String res = HttpUtils.simpleGet(url);
			System.out.println(res.length());
			JSONObject resx = JSONObject.fromObject(res);
			System.out.println(resx.getBoolean("flag"));
			JSONArray fields = resx.getJSONArray("fields");
			for (int i = 0; i < fields.size(); i++) {
				ObservableList<StringProperty> row = FXCollections.observableArrayList();
				JSONArray field = fields.getJSONArray(i);
				for (int j = 0; j < field.size(); j++) {
					row.add(new SimpleStringProperty(field.getString(j)));
				}
				rowList.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rowList;
	}

	@Override
	public String getDB() {
		return db;
	}

	@Override
	public String getEntityName() {
		return table;
	}

}
