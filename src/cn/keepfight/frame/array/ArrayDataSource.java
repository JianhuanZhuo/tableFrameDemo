package cn.keepfight.frame.array;

import java.util.Set;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.content.source.InvalidSourceException;

public abstract class ArrayDataSource implements DataSource {

	@Override
	public DataSourceType getSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSourceIDName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkValid() throws InvalidSourceException {
		// TODO Auto-generated method stub

	}

	public <T> void add(T d) {

	}

}
