package cn.keepfight.frame.chain;

import java.util.List;

/**
 * ��Դ����BUS
 * @author Tom
 *
 */
public class ResourceBus {

	/**
	 * ����ģʽ֧��
	 */
	private static ResourceBus instance = new ResourceBus();
	public static ResourceBus getInstance() {
		return instance;
	}

	/**
	 * ��ȡ��Դ
	 * @return
	 */
	public List<Resource> pullResources() {
		return null;
	}
}
