package cn.keepfight.frame.chain;

import java.util.List;

/**
 * 资源运送BUS
 * @author Tom
 *
 */
public class ResourceBus {

	/**
	 * 单例模式支持
	 */
	private static ResourceBus instance = new ResourceBus();
	public static ResourceBus getInstance() {
		return instance;
	}

	/**
	 * 拉取资源
	 * @return
	 */
	public List<Resource> pullResources() {
		return null;
	}
}
