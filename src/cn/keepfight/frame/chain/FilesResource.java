package cn.keepfight.frame.chain;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.files.FilesDataSource;

public class FilesResource extends Resource{

	List<File> targetList;

	public FilesResource(List<File> targetList) {
		this.targetList = targetList;
	}

	@Override
	public DataSourceType getDataSourceType() {
		return DataSourceType.FILES;
	}

	@Override
	public DataSource generateDataSource() {
		return new FilesDataSource() {

			@Override
			public String getSourceIDName() {
				return getName();
			}

			@Override
			public Set<File> getFiles() {
				return new HashSet<File>(targetList);
			}
		};
	}

}
