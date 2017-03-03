package cn.keepfight.frame.content.source;

/**
 * 数据源接口.
 * 定义了作为一个数据源该有的行为和属性。
 *
 * @author Tom
 *
 */
public interface DataSource {

	/**
	 * 获得该数据源的类型。
	 * @return 数据源类型
	 */
	public abstract DataSourceType getSourceType();

	/**
	 * 检查数据源的有效性进行简单的检查。
	 */
	public void checkValid() throws InvalidSourceException;
}
