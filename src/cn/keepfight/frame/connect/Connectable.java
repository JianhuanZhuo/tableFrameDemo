package cn.keepfight.frame.connect;

import java.io.IOException;
import net.sf.json.JSONObject;

/**
 * �����ӽӿ�
 * @author Tom
 *
 */
public interface Connectable {

	/**
	 * �����ӣ�һ�����Ҫ��ʹ����������֮ǰ��
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
	 * ָ��URL��paramList���򵥵�get������������ JSON ����
	 * @param url get������ַ
	 * @param paramList �����б��ַ���
	 * @return json����Ĭ�ϵģ�������������� flag ��ʾ�ôβ����Ƿ�ɹ���
	 */
	public JSONObject simpleGet(String url, String paramList);

	/**
	 * �ر����ӣ�һ�����Ҫ��ʹ������������֮����ã��Ա�֪ͨ�÷�����Խ��������ͷ������Դ
	 * @throws IOException IO�쳣
	 */
	public void close() throws IOException;
}
