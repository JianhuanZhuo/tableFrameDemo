package cn.keepfight.frame.text;

import java.io.File;
import java.io.FileReader;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.content.source.InvalidSourceException;

/**
 * 文本文件数据源
 * @author Tom
 *
 */
public abstract class TextDataSource implements DataSource {

	@Override
	public DataSourceType getSourceType() {
		return DataSourceType.TEXT;
	}


	@Override
	public void checkValid() throws InvalidSourceException {
	}


	public abstract File getFile();
}
