package cn.keepfight.operator;

import java.util.List;

import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.chain.Resource;

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
	 * @return �������������������Դ������������Դ������ڲ����򷵻�null
	 * @exception Exception ���������в����ĸ����쳣
	 */
	public abstract List<Resource> onAction() throws Exception ;

	/**
	 * ����������Դ
	 * @return �ɸ�����״̬���ɵ�������Դ
	 */
	public OperatorResource generateResource(){
		OperatorResource res = new OperatorResource(getId(), getName());
		res.setDescription(getDescription());
		res.setLabel(getLabel());
		res.setIcon(getIcon());
		res.setInputResource(new String[0]);
		res.setOutputResource(new String[0]);
		res.setParams(new String[0]);
		return res;
	}
}
