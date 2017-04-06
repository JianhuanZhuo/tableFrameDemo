package cn.keepfight.frame.chain;

import java.util.List;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.content.source.InvalidSourceException;

/**
 * 算子链数据源类，用于描述链数据源该有的功能
 * @author Tom
 *
 */
public abstract class ChainDataSource implements DataSource{

	/**
	 * 资源描述类
	 * @author Tom 指明在该链中资源的位置和类型
	 *
	 */
	class ResourceWithPosition{
		Resource resource;
		double x;
		double y;
	}

	/**
	 * 连接边描述类
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
	 * 获得链数据源提供资源列表
	 * @return 资源列表
	 */
	public abstract List<ResourceWithPosition> getResources();

	/**
	 * 获得得链数据源提供连接边列表
	 * @return 连接边列表
	 */
	public abstract List<Edge> getEdges();
}
