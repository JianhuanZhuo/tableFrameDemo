package cn.keepfight.frame.table;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cn.keepfight.frame.connect.db.JDBCConnector;
import cn.keepfight.frame.content.source.InvalidSourceException;
import cn.keepfight.operator.WaitDialog;
import cn.keepfight.utils.HttpUtils;
import cn.keepfight.utils.SimpleSQLUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.util.Pair;
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
		String url = "http://127.0.0.1:8080/dap/dataLoad/getCloum.htm";
		String res;
		try {
			List<Pair<String, String>> params = new ArrayList<>();
			params.add(new Pair<String, String>("db", db));
			params.add(new Pair<String, String>("tableName", table));
			res = HttpUtils.simpleGetWithEncode(url, params);
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
//		String viewName = new WaitDialog<String>(new Task<String>() {
//			@Override
//			protected String call() throws Exception {
//				SimpleSQLUtils simpleSQL = SimpleSQLUtils.build(new JDBCConnector().getConnection()).setDB("wz");
//				String tableName = "table_"+System.currentTimeMillis();
//				simpleSQL.createView(sqlString);
//				return tableName;
//			}
//		}).justWait();
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
//		try {
//			String url = "http://127.0.0.1:8080/dap/dataLoad/getTableContent.htm?table="+table+"&start="+startRow+"&limit="+limit;
//			String res = HttpUtils.simpleGet(url);
//			System.out.println(res.length());
//			JSONObject resx = JSONObject.fromObject(res);
//			System.out.println(resx.getBoolean("flag"));
//			JSONArray fields = resx.getJSONArray("fields");
//			for (int i = 0; i < fields.size(); i++) {
//				ObservableList<StringProperty> row = FXCollections.observableArrayList();
//				JSONArray field = fields.getJSONArray(i);
//				for (int j = 0; j < field.size(); j++) {
//					row.add(new SimpleStringProperty(field.getString(j)));
//				}
//				rowList.add(row);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		try {
			SimpleSQLUtils simpleSQL = SimpleSQLUtils.build(new JDBCConnector().getConnection()).setDB("wz");
			rowList = simpleSQL.select(table, startRow, limit).stream().map(row->{
				ObservableList<StringProperty> observableList = FXCollections.observableArrayList();
				row.stream()
//					.peek(s->System.out.println(s))
					.map(s->new SimpleStringProperty(s)).forEach(p->observableList.add(p));
				return observableList;
			}).collect(Collectors.toList());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
