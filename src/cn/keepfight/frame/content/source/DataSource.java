package cn.keepfight.frame.content.source;

/**
 * ����Դ�ӿ�.
 * ��������Ϊһ������Դ���е���Ϊ�����ԡ�
 *
 * @author Tom
 *
 */
public interface DataSource {

	/**
	 * ��ø�����Դ�����͡�
	 * @return ����Դ����
	 */
	public abstract DataSourceType getSourceType();

	/**
	 * �������Դ����Ч�Խ��м򵥵ļ�顣
	 */
	public void checkValid() throws InvalidSourceException;
}
