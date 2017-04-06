package cn.keepfight.frame.chain;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.DataSourceType;

/**
 * 抽象资源类
 * @author Tom
 *
 */
public abstract class Resource {

	private static int id_count=1;

	int id = id_count++;

	/**
	 * 获得资源类型
	 * @return 资源类型
	 */
	public abstract DataSourceType getDataSourceType();

	/**
	 * 生成数据源
	 * @return 数据源
	 */
	public abstract DataSource generateDataSource();

	/**
	 * 默认的图标URL，具体资源需要重载
	 * @return 图标URL
	 */
	public String getIconURL() {
		return getDataSourceType().getIconURL();
	}

	/**
	 * 获得默认资源实例名
	 * @return 资源实例名字符串
	 */
	public String getName() {
		return getDataSourceType().getTypeName_cn()+"——"+id;
	}
}
