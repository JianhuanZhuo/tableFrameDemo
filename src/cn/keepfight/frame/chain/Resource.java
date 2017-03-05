package cn.keepfight.frame.chain;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.DataSourceType;

/**
 * ������Դ��
 * @author Tom
 *
 */
public abstract class Resource {

	private static int id_count=1;

	int id = id_count++;

	/**
	 * �����Դ����
	 * @return ��Դ����
	 */
	public abstract DataSourceType getDataSourceType();

	/**
	 * ��������Դ
	 * @return ����Դ
	 */
	public abstract DataSource generateDataSource();

	/**
	 * Ĭ�ϵ�ͼ��URL��������Դ��Ҫ����
	 * @return ͼ��URL
	 */
	public String getIconURL() {
		return getDataSourceType().getIconURL();
	}

	/**
	 * ���Ĭ����Դʵ����
	 * @return ��Դʵ�����ַ���
	 */
	public String getName() {
		return getDataSourceType().getTypeName_cn()+"����"+id;
	}
}
