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
	DataSourceType type;

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
		return type.getIconURL();
	}

	/**
	 * ���Ĭ����Դʵ����
	 * @return ��Դʵ�����ַ���
	 */
	public String getName() {
		return type.getTypeName_cn()+"����"+id;
	}
}
