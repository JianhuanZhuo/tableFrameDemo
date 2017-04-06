package cn.keepfight.frame.connect;

import java.io.IOException;
import net.sf.json.JSONObject;

/**
 * 可连接接口
 * @author Tom
 *
 */
public interface Connectable {

	/**
	 * 打开连接，一般的需要在使用其他操作之前打开
	 * @throws IOException IO异常
	 */
	public void open() throws IOException;

	/**
	 * 简单交换方法，发送指定字符串并获得回应。
	 * @param whisper 欲发送的字符串
	 * @return 所得回应。
	 */
	public String simpleSwitch(String whisper);

	/**
	 * 指定URL和paramList作简单的get操作，并返回 JSON 对象。
	 * @param url get操作地址
	 * @param paramList 参数列表字符串
	 * @return json对象，默认的，对象根存在属性 flag 表示该次操作是否成功。
	 */
	public JSONObject simpleGet(String url, String paramList);

	/**
	 * 关闭连接，一般的需要在使用完其他操作之后调用，以便通知该服务可以结束服务，释放相关资源
	 * @throws IOException IO异常
	 */
	public void close() throws IOException;
}
