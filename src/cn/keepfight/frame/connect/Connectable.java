package cn.keepfight.frame.connect;

import java.io.IOException;

/**
 * 可连接接口
 * @author Tom
 *
 */
public interface Connectable {

	/**
	 * 打开连接
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
	 * 关闭连接
	 * @throws IOException IO异常
	 */
	public void close() throws IOException;
}
