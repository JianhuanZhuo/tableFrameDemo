package cn.keepfight.frame;

import java.util.List;

import cn.keepfight.frame.chain.Resource;
import cn.keepfight.frame.content.source.DataSource;

/**
 * ���ݹ����߽ӿ�
 * @author Tom
 *
 */
public interface ContextMaster {

	public void update(ContextSlave slave);

	/**
	 * ����ִ���������㱨<br/>
	 * ���ط��������������ִ��
	 * @param slave ִ�и�����������
	 * @param operator ����������Դ����Ҫ��������������ͼ���
	 * @param result ������
	 * @return ����������������������ɵ�����Դ
	 */
	public DataSource doOperate(ContextSlave slave, Resource operator, Resource result);

	/**
	 * ����ִ���������㱨<br/>
	 * ���ط��������������ִ��
	 * @param slave ִ�и�����������
	 * @param operator ����������Դ����Ҫ��������������ͼ���
	 * @param results ����������
	 * @return ��������������������������ɵ�����Դ���ϣ�˳����һ��
	 */
	public List<DataSource> doOperate(ContextSlave slave, Resource operator, List<Resource> results);

	/**
	 * ����ִ���������㱨<br/>
	 * ���ط�������������������ִ��
	 * @param slave ִ�и����������弯��
	 * @param operator ����������Դ����Ҫ��������������ͼ���
	 * @param results ����������
	 * @return ��������������������������ɵ�����Դ���ϣ�˳����һ��
	 */
	public List<DataSource> doOperate(List<ContextSlave> slaves, Resource operator, List<Resource> results);
}
