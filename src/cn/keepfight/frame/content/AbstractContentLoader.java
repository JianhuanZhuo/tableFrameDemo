package cn.keepfight.frame.content;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;

/**
 * ���ݼ�����������.
 * @author Tom
 * @param <T> ����Դ����
 *
 */
public abstract class AbstractContentLoader<T extends DataSource>{


	/**
	 * ����Դ����
	 */
	protected T source;

	/**
	 * ��������Դ���������Դ����һ�μ�飬�����Զ�����
	 * @param source �����õ�����Դ
	 * @throws InvalidSourceException ����Դ��Ч�쳣
	 */
	public void setDataSource(T source) throws InvalidSourceException{
		source.checkValid();
		this.source = source;
		clear();
	}

	public T getSource(){return source;}

	/**
	 * �����ݽ������¼��ء�
	 */
	public abstract void reload();

	/**
	 * ɾ�����ݼ�����������Դ�����������ݡ�
	 */
	public abstract void clear();
}
