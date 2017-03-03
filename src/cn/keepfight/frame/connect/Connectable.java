package cn.keepfight.frame.connect;

import java.io.IOException;

/**
 * �����ӽӿ�
 * @author Tom
 *
 */
public interface Connectable {

	/**
	 * ������
	 * @throws IOException IO�쳣
	 */
	public void open() throws IOException;

	/**
	 * �򵥽�������������ָ���ַ�������û�Ӧ��
	 * @param whisper �����͵��ַ���
	 * @return ���û�Ӧ��
	 */
	public String simpleSwitch(String whisper);



	/**
	 * �ر�����
	 * @throws IOException IO�쳣
	 */
	public void close() throws IOException;
}
