package cn.keepfight.frame.chain;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.table.DBTableDataSource;

public class TableResource extends Resource {

	/**
	 * @TODO 此处需要改进以便支持更广泛的接入
	 */
	String db;
	String table;

	/**
	 * 指定数据库与数据表名创建一个表资源
	 * @param db 数据库名
	 * @param table 数据表名
	 */
	public TableResource(String db, String table) {
		this.db = db;
		this.table = table;
	}

	@Override
	public DataSourceType getDataSourceType() {
		return DataSourceType.TABLE;
	}

	@Override
	public DataSource generateDataSource() {
		return new DBTableDataSource(db, table);
	}

	@Override
	public String getName() {
		return db+"\\"+table;
	}
}
