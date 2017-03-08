package cn.keepfight.frame.chain;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.table.DBTableDataSource;

public class TableResource extends Resource {

	/**
	 * @TODO �˴���Ҫ�Ľ��Ա�֧�ָ��㷺�Ľ���
	 */
	String db;
	String table;

	/**
	 * ָ�����ݿ������ݱ�������һ������Դ
	 * @param db ���ݿ���
	 * @param table ���ݱ���
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
