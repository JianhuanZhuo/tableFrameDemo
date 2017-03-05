package cn.keepfight.frame.chain;

import java.util.List;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.content.source.InvalidSourceException;

/**
 * ����������Դ�࣬��������������Դ���еĹ���
 * @author Tom
 *
 */
public abstract class ChainDataSource implements DataSource{

	/**
	 * ��Դ������
	 * @author Tom ָ���ڸ�������Դ��λ�ú�����
	 *
	 */
	class ResourceWithPosition{
		Resource resource;
		double x;
		double y;
	}

	/**
	 * ���ӱ�������
	 * @author Tom
	 *
	 */
	class Edge {
		Resource start;
		Resource end;
	}

	@Override
	public DataSourceType getSourceType() {
		return DataSourceType.CHAIN;
	}

	@Override
	public void checkValid() throws InvalidSourceException {
	}

	/**
	 * ���������Դ�ṩ��Դ�б�
	 * @return ��Դ�б�
	 */
	public abstract List<ResourceWithPosition> getResources();

	/**
	 * ��õ�������Դ�ṩ���ӱ��б�
	 * @return ���ӱ��б�
	 */
	public abstract List<Edge> getEdges();
}
