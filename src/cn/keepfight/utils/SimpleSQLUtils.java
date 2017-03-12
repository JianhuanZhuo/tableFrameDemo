package cn.keepfight.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cn.keepfight.frame.connect.db.JDBCConnector;

/**
 * 简单SQL语句工具类<br/>
 * 用于一般简单的数据库查询，如：
 * <ol>
 * <li>表存在与否查询</li>
 * <li>指定模式数据记录数查询</li>
 * <li>执行插入记录</li>
 * </ol>
 * 该工具类下的方法全部使用静态方法提供。
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

        //构建创建表SQL语句
        String createSql = "create table " + DB+"."+tableName + " " +
                (construct.equals("") ? "" : (" (" + construct.substring(1) + ") ")) +
                (tableSchema == null ? "" : tableSchema);

        System.out.println("创建数据表的sql="+createSql);
			return cnt.prepareStatement(createSql).execute();
    }


    /**
     * 检查中的数据库中是否存在指定表名的表
     *
     * @param dbName    数据库名
     * @param tableName 指定表的表名
     * @return 存在返回true，否则返回false。
     * @throws SQLException 检查数据库查询异常。
     */
    public boolean isExistTable(String tableName) throws SQLException {
    	String dbStr = DB;
    	DB = "INFORMATION_SCHEMA";
        boolean isExist = isExistRecord("TABLES", "TABLE_NAME = '" + tableName + "'"+" and TABLE_SCHEMA='"+dbStr+"'") != 0;
        DB = dbStr;
        return isExist;
    }

    /**
     * 提供指定表中匹配记录记录的查询。<br/>
     * 重载方法，推荐使用。传入 BasicDao 而不是数据库名已提高数据库查询效率。<br/>
     * 注意该方法不会关闭传入的数据库，需要用户自行关闭。<br/>
     * 一个简单的例子，用于查询students_table表是否存在：<pre>
     *      isExistRecord(basicDao, "INFORMATION_SCHEMA.TABLES", "TABLE_NAME = 'students_table'")
     * </pre>
     * 该方法将生成如下语句进行查询，并将<code>count</code>字段的结果进行返回。
     * <pre>
     *      select count(*) count from INFORMATION_SCHEMA.TABLES where TABLE_NAME = 'students_table';"
     * </pre>
     *
     * @param basicDao   指定数据库句柄
     * @param tableName  指定表
     * @param SQLpattern 欲匹配的查询模式，若该参数空字符串（非NULL），则查询全部记录。
     * @return 查询指定模式的记录值，所以0表示无匹配记录，大于0的返回值表示存在匹配记录。
     * @throws SQLException 简单查询数据库错误
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
     * 向指定数据库中的指定表插入数据。<br/>
     * 重载方法，使用 head 列表指定位置顺序进行插入操作<br/>
     * 该方法使用基础dao以提高数据库访问效率，注意该方法不会关闭dao，需要用户自行关闭<br/>
     * <code>record</code>中的元素都必须是字符串类，其他类型需转化为字符串类型再进行插入。
     *
     * @param basicDao  基础dao
     * @param tableName 欲插入数据的表名
     * @param record    元素为插入数据的列表
     * @param head      指定位置顺序的列表，head为null表示使用默认顺序，与{@link #insert(BasicDao, String, List)}等同。
     * @throws SQLException 检查数据库查询错误
     */
    public void insert(String tableName, List<String> record, List<String> head) throws SQLException {
        if (tableName == null || tableName.equals("") || record == null) {
            throw new SQLException("param error!");
        }

        String valueStr = record.stream().map(s->"'"+s+"'").collect(Collectors.joining(",", "(", ")"));
        String headStr = head==null?"":head.stream().collect(Collectors.joining(",", "(", ")"));

        String insertSql = "insert into " + DB+"."+tableName + " " + headStr + " values " + valueStr;

        //执行插入
        cnt.prepareStatement(insertSql).executeUpdate();
    }


    /**
     * 查询指定数据库、指定表的字段名信息
     * @param db 指定数据库
     * @param table 指定表
     * @return 一个字段信息列表
     * @throws SQLException 简单数据库查询错误
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
