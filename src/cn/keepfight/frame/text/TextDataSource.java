package cn.keepfight.frame.text;

import java.io.FileReader;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.content.source.InvalidSourceException;

/**
 * �ı��ļ�����Դ
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

	public abstract FileReader getReader();
}
