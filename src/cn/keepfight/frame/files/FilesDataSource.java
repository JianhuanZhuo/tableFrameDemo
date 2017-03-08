package cn.keepfight.frame.files;

import java.io.File;
import java.util.Set;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.content.source.InvalidSourceException;

public abstract class FilesDataSource implements DataSource {

	private Set<File> files;

	@Override
	public DataSourceType getSourceType() {
		return DataSourceType.FILES;
	}

	@Override
	public void checkValid() throws InvalidSourceException {
	}

	public abstract Set<File> getFiles();

	public void setFiles(Set<File> files){
		this.files = files;
	}
}
