package cn.keepfight.frame.connect;

/**
 * ���ӷ��������
 * @author Tom
 *
 */
public class ConnectManager {

	private static ConnectManager instance = new ConnectManager();
	/**
	 * ����ģʽ֧��
	 * @return
	 */
	public static ConnectManager getInstance() {
		return instance;
	}

	/**
	 * ���Ĭ�����ӷ�ʽ�Ŀ����Ӷ���
	 * @return �����Ӷ���
	 */
	public Connectable getDefault() {
		//@TODO �������ӳط�ʽ
		return new MemoryConnect();
	}
}
