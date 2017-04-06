package cn.keepfight.frame.connect.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import cn.keepfight.utils.PropertieUtil;

/**
 * JDBC连接器
 *
 * @author Tom
 * @TODO update to use connection pool / adapt to more DBMS
 */
public class JDBCConnector {

	public String dbms;
	public String jarFile;
	public String dbName;
	public String userName;
	public String password;
	public String urlString;

	private String driver;
	private String serverName;
	private int portNumber;
	private Properties prop;

	/**
	 * support to single instance mode
	 */
	private static JDBCConnector instance = new JDBCConnector();
	/***************************************************************************************
	 * static statements
	 ***************************************************************************************/
	static {
	     try {
	        Class.forName("com.mysql.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        throw new IllegalArgumentException("MySQL db driver isnot on classpath");
	    }
	}

	/**
	 * get Connector instance of JDBCConnector
	 * @return Connector instance
	 */
	public static JDBCConnector getConnector() {
		return instance;
	}
	/***************************************************************************************
	 * construction implements
	 ***************************************************************************************/

	public JDBCConnector(){
		this("properties.xml");
	}

	/**
	 * 使用指定文件进行初始化配置
	 *
	 * @param configureFile
	 * @throws Exception
	 */
	public JDBCConnector(String configureFile){
		setProperties(configureFile);
	}

	/***************************************************************************************
	 * public method implements
	 ***************************************************************************************/

	/**
	 * create a connection to DB without specifying a DB, <br/>
	 * so you must do by yourself on your SQL statements<br/>
	 * @return connection to DB
	 * @throws SQLException SQL exception occurring during creating connection
	 */
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", this.userName);
		connectionProps.put("password", this.password);

		String connectURL = "jdbc:" + this.dbms + "://" + this.serverName + ":" + this.portNumber + "/";
		conn = DriverManager.getConnection(connectURL, connectionProps);

		System.out.println("Connected to database without specify a dbname!");
		return conn;
	}

//	/**
//	 * create a connection to DB by specifying a DB which using a property value from properties file.<br/>
//	 * something must to be payed attention is user couldn't know whether the return object has been added<br/>
//	 * the DB to connection URL.
//	 * @return connection to DB
//	 * @throws SQLException SQL exception occurring during creating connection
//	 */
//	public Connection getConnectionToDB() throws SQLException {
//		Connection conn = null;
//		Properties connectionProps = new Properties();
//		connectionProps.put("user", this.userName);
//		connectionProps.put("password", this.password);
//		String enCoding = "?useUnicode=true&characterEncoding=utf8&autoReconnect=true&maxReconnects=3&useSSL=false";
//
//		// connection url string
//		String connectURL = "jdbc:" + dbms + "://" + serverName + ":" + portNumber + "/" + dbName+enCoding;
//
//		conn = DriverManager.getConnection(connectURL, connectionProps);
//		conn.setCatalog(this.dbName);
//		return conn;
//	}

	/***************************************************************************************
	 * private method implements
	 ***************************************************************************************/

	/**
	 *
	 * @param fileName
	 * @throws Exception
	 */
	private void setProperties(String fileName){

		try {
			prop = PropertieUtil.loadProperties(fileName, "properties.xml");
		} catch (Exception e) {
			prop = new Properties();
			prop.setProperty("dbms", "mysql");
			prop.setProperty("driver", "com.mysql.jdbc.Driver");
			prop.setProperty("database_name", "wz");
			prop.setProperty("user_name", "root");
			prop.setProperty("password", "123lyz");
			prop.setProperty("server_name", "10.10.6.11");
			prop.setProperty("port_number", "3306");
			System.err.println("Failed to read properties file on building JDBC connector! Use coding properties!");
			e.printStackTrace();
		}

		this.dbms = this.prop.getProperty("dbms");
		this.jarFile = this.prop.getProperty("jar_file");
		this.driver = this.prop.getProperty("driver");
		this.dbName = this.prop.getProperty("database_name");
		this.userName = this.prop.getProperty("user_name");
		this.password = this.prop.getProperty("password");
		this.serverName = this.prop.getProperty("server_name");
		this.portNumber = Integer.parseInt(this.prop.getProperty("port_number"));

//		System.out.println("Set the following properties:");
//		System.out.println("dbms: " + dbms);
//		System.out.println("driver: " + driver);
//		System.out.println("dbName: " + dbName);
//		System.out.println("userName: " + userName);
//		System.out.println("serverName: " + serverName);
//		System.out.println("portNumber: " + portNumber);
	}
}
