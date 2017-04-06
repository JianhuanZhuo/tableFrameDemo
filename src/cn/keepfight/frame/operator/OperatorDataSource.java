package cn.keepfight.frame.operator;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.content.source.InvalidSourceException;

/**
 * 抽象算子数据源类
 * @author Tom
 *
 */
public abstract class OperatorDataSource implements DataSource{

	@Override
	public DataSourceType getSourceType() {
		return DataSourceType.OPERATOR;
	}

	@Override
	public void checkValid() throws InvalidSourceException {
	}

	public abstract int getId();
	public abstract String getLabel();
	public abstract String getName();
	public abstract String getIcon();
	public abstract String getDescription();
	public abstract String[] getInputResource();
	public abstract String[] getOutputResource() ;
	public abstract String[] getParams() ;
}
