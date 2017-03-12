package cn.keepfight.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cn.keepfight.frame.connect.db.JDBCConnector;

/**
 * ��SQL��乤����<br/>
 * ����һ��򵥵����ݿ��ѯ���磺
 * <ol>
 * <li>���������ѯ</li>
 * <li>ָ��ģʽ���ݼ�¼����ѯ</li>
 * <li>ִ�в����¼</li>
 * </ol>
 * �ù������µķ���ȫ��ʹ�þ�̬�����ṩ��
 * <br/>Created by TOM on 2017/1/21.
 *
 * @TODO update to support cache prepare statement of 100 selection
 */
public class SimpleSQLUtils {

	private Connection cnt;
	private String DB;

	/***************************************************************************************
	 * static implements
	 ***************************************************************************************/
    public static SimpleSQLUtils build(Connection cnt) {
    	SimpleSQLUtils instance = new SimpleSQLUtils();
    	return instance;
	}


	/***************************************************************************************
	 * public implements
	 ***************************************************************************************/
    public SimpleSQLUtils setConnection(Connection cnt) {
    	this.cnt = cnt;
    	return this;
	}


    public SimpleSQLUtils setDB(String DB) {
    	this.DB = DB;
    	return this;
	}

    public boolean createTable(String tableName, List<String> fieldConstruct, String tableSchema) throws SQLException {
        if (tableName == null || fieldConstruct == null || tableName.equals("")) {
            throw new SQLException("param error!");
        }

        System.out.println("f1");
        checkConnect();

        String construct = fieldConstruct.stream()
        	.filter(f->(f!=null&&f.trim().length()!=0))
        	.collect(Collectors.joining(","));

        //����������SQL���
        String createSql = "create table " + DB+"."+tableName + " " +
                (construct.equals("") ? "" : (" (" + construct.substring(1) + ") ")) +
                (tableSchema == null ? "" : tableSchema);

        System.out.println("�������ݱ��sql="+createSql);
			return cnt.prepareStatement(createSql).execute();
    }


    /**
     * ����е����ݿ����Ƿ����ָ�������ı�
     *
     * @param dbName    ���ݿ���
     * @param tableName ָ����ı���
     * @return ���ڷ���true�����򷵻�false��
     * @throws SQLException ������ݿ��ѯ�쳣��
     */
    public boolean isExistTable(String tableName) throws SQLException {
    	String dbStr = DB;
    	DB = "INFORMATION_SCHEMA";
        boolean isExist = isExistRecord("TABLES", "TABLE_NAME = '" + tableName + "'"+" and TABLE_SCHEMA='"+dbStr+"'") != 0;
        DB = dbStr;
        return isExist;
    }

    /**
     * �ṩָ������ƥ���¼��¼�Ĳ�ѯ��<br/>
     * ���ط������Ƽ�ʹ�á����� BasicDao ���������ݿ�����������ݿ��ѯЧ�ʡ�<br/>
     * ע��÷�������رմ�������ݿ⣬��Ҫ�û����йرա�<br/>
     * һ���򵥵����ӣ����ڲ�ѯstudents_table���Ƿ���ڣ�<pre>
     *      isExistRecord(basicDao, "INFORMATION_SCHEMA.TABLES", "TABLE_NAME = 'students_table'")
     * </pre>
     * �÷������������������в�ѯ������<code>count</code>�ֶεĽ�����з��ء�
     * <pre>
     *      select count(*) count from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'students_table';"
     * </pre>
     *
     * @param basicDao   ָ�����ݿ���
     * @param tableName  ָ����
     * @param SQLpattern ��ƥ��Ĳ�ѯģʽ�����ò������ַ�������NULL�������ѯȫ����¼��
     * @return ��ѯָ��ģʽ�ļ�¼ֵ������0��ʾ��ƥ���¼������0�ķ���ֵ��ʾ����ƥ���¼��
     * @throws SQLException �򵥲�ѯ���ݿ����
     */
    public int isExistRecord(String tableName, String SQLpattern) throws SQLException {
        if (tableName == null || tableName.equals("") || SQLpattern == null) {
            throw new SQLException("param not available");
        }

        // check if connection have been set.
        checkConnect();

        String sql = "select count(*) count from " + DB+"."+tableName;
        if (!SQLpattern.equals("")) {
            sql = sql + " where " + SQLpattern + ";";
        }
//        System.out.println(sql);
        ResultSet res = cnt.prepareStatement(sql).executeQuery();
        if (res.next()) {
            return res.getInt("count");
        } else {
            throw new SQLException();
        }
    }

    /**
     * ��ָ�����ݿ��е�ָ����������ݡ�<br/>
     * ���ط�����ʹ�� head �б�ָ��λ��˳����в������<br/>
     * �÷���ʹ�û���dao��������ݿ����Ч�ʣ�ע��÷�������ر�dao����Ҫ�û����йر�<br/>
     * <code>record</code>�е�Ԫ�ض��������ַ����࣬����������ת��Ϊ�ַ��������ٽ��в��롣
     *
     * @param basicDao  ����dao
     * @param tableName ���������ݵı���
     * @param record    Ԫ��Ϊ�������ݵ��б�
     * @param head      ָ��λ��˳����б�headΪnull��ʾʹ��Ĭ��˳����{@link #insert(BasicDao, String, List)}��ͬ��
     * @throws SQLException ������ݿ��ѯ����
     */
    public void insert(String tableName, List<String> record, List<String> head) throws SQLException {
        if (tableName == null || tableName.equals("") || record == null) {
            throw new SQLException("param error!");
        }

        String valueStr = record.stream().map(s->"'"+s+"'").collect(Collectors.joining(",", "(", ")"));
        String headStr = head==null?"":head.stream().collect(Collectors.joining(",", "(", ")"));

        String insertSql = "insert into " + DB+"."+tableName + " " + headStr + " values " + valueStr;

        //ִ�в���
        cnt.prepareStatement(insertSql).executeUpdate();
    }


    /**
     * ��ѯָ�����ݿ⡢ָ������ֶ�����Ϣ
     * @param db ָ�����ݿ�
     * @param table ָ����
     * @return һ���ֶ���Ϣ�б�
     * @throws SQLException �����ݿ��ѯ����
     */
    public List<String> getFieldNames(String table) throws SQLException {
        if (table == null ||  table.equals("")) {
            throw new SQLException("param error");
        }

        checkConnect();

        List<String> res = new ArrayList<>();
        if (!isExistTable(table)) {
            throw new SQLException("table "+table+" is not exist!");
        }

        String sql = "SELECT `COLUMN_NAME` \n" +
                "FROM `INFORMATION_SCHEMA`.`COLUMNS` \n" +
                "WHERE `TABLE_SCHEMA`='" + DB + "' \n" +
                "    AND `TABLE_NAME`='" + table + "';";

        ResultSet resultSet = cnt.prepareStatement(sql).executeQuery();
        while(resultSet.next()){
            res.add(resultSet.getString("COLUMN_NAME"));
        }

        return res;
    }

    /**
     * @TODO specify the type
     * @param table
     * @param start
     * @param limit
     * @return
     * @throws SQLException
     */
    public  List<List<String>> select(String table, int start, int limit)throws SQLException {
        //@TODO check parameters of start and limit
    	if (table == null || table.equals("")) {
            throw new SQLException("param error");
        }

    	checkConnect();

    	List<List<String>> res = new ArrayList<>();
        if (!isExistTable(table)) {
            throw new SQLException("table "+table+" is not exist!");
        }

        String sql = "select * from "+DB+"."+table+" limit "+start+","+limit+";";
//        System.out.println("sql= "+sql);
        ResultSet resultSet = cnt.prepareStatement(sql).executeQuery();

        int cols = resultSet.getMetaData().getColumnCount();
        while(resultSet.next()){
            List<String> row = new ArrayList<>();
            for (int i = 0; i <cols; i++) {
                row.add(resultSet.getString(i+1));
            }
            res.add(row);
        }
        return res;
    }


    public int getRowNum(String table) throws SQLException {
        if (table == null || table.equals("")) {
            throw new SQLException("param error");
        }

        checkConnect();

        if (!isExistTable(table)) {
            throw new SQLException("table "+table+" is not exist!");
        }


        String sql = "SELECT count(*) from "+DB+"."+table + ";";

        ResultSet resultSet = cnt.prepareStatement(sql).executeQuery();
        while(resultSet.next()){
            return  resultSet.getInt("count(*)");
        }

        return -1;
    }

    public String createView(String sql) throws SQLException {
        if (sql == null || sql.equals("")) {
            throw new SQLException("param error");
        }

        checkConnect();

        String viewName = "view_temp"+System.currentTimeMillis();

        String createSQL = "create view "+DB+"."+viewName+" as "+sql+";";

        cnt.createStatement().execute(createSQL);

        System.out.println("createSQL="+createSQL);
        return viewName;
    }


    public String saveAs(String name) throws SQLException {
        if (name == null || name.equals("")) {
            throw new SQLException("param error");
        }

        String createName = "table_temp_"+System.currentTimeMillis();
        String createSQL = "create table "+DB+"."+createName+" as select * from "+name+";";

        cnt.createStatement().execute(createSQL);

        return createName;
    }

    public List<List<String>> showTables() throws SQLException {

    	checkConnect();

        List<List<String>> resList = new ArrayList<>();

        ResultSet resultSet = cnt.prepareStatement("SHOW FULL TABLES IN "+DB).executeQuery();
        while(resultSet.next()){
            List<String> row = new ArrayList<>();
            row.add(resultSet.getString("Tables_in_"+DB));
            row.add(resultSet.getString("Table_type"));
            resList.add(row);
        }
        return resList;
    }


    public void loadData(String filePath, String table, String args) throws SQLException {

    	checkConnect();

    	String sql = "load data local infile '"+filePath.replace("\\", "/")+"' into table "+DB+"."+table+" "+args;
    	cnt.createStatement().execute(sql);
	}

	/***************************************************************************************
	 * private implements
	 ***************************************************************************************/
    private void checkConnect() throws SQLException{
    	if (cnt==null) {
    		if (DB==null || DB.equals("")) {
    			throw new RuntimeException("Connection and DBname are both null!");
			}else {
	    		cnt = JDBCConnector.getConnector().getConnection();
			}
		}
	}
}
