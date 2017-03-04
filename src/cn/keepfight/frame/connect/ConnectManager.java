package cn.keepfight.frame.connect;

/**
 * 连接服务管理器
 * @author Tom
 *
 */
public class ConnectManager {

	private static ConnectManager instance = new ConnectManager();
	/**
	 * 单例模式支持
	 * @return
	 */
	public static ConnectManager getInstance() {
		return instance;
	}

	/**
	 * 获得默认连接方式的可连接对象
	 * @return 可连接对象
	 */
	public Connectable getDefault() {
		//@TODO 做成连接池方式
		return new MemoryConnect();
	}
}
