package cn.keepfight.operator;

import cn.keepfight.frame.chain.OperatorResource;

/**
 * ����ģ�͵������ࡣ
 * @author Tom
 *
 */
public abstract class AbstractOperator{

	/**
	 * ������� ID
	 * @return ���� ID
	 */
	public abstract int getId();

	/**
	 * �����������ָ���ӵı�ʶ�������зָ������� spilt
	 * @return ���ӵı�ʶ��
	 */
	public abstract String getName();

	/**
	 * �����������������ʾ�ģ����зָ������� "�зָ�"
	 * @return ������ʾ��������
	 */
	public abstract String getLabel();

	/**
	 * �������ͼ��
	 * @return ����ͼ��
	 */
	public abstract String getIcon();

	/**
	 * ���������ʾ�ַ���
	 * @return ������ʾ�ַ���
	 */
	public abstract String getTips();

	/**
	 * ������������ַ���
	 * @return ���ӵĽ���˵���ַ���
	 */
	public abstract String getDescription();

	/**
	 * �涨�ڵ��������ʱ��ָ���Ķ���
	 * @TODO ����ӿڵĹ淶ò�ƻ���Ҫ�������
	 */
	public abstract void onAction();

	/**
	 * ����������Դ
	 * @return �ɸ�����״̬���ɵ�������Դ
	 */
	public abstract OperatorResource generateResource();
}
