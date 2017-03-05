package cn.keepfight.frame.chain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.DataSourceType;
import cn.keepfight.frame.text.TextDataSource;

public class TextResouce extends Resource{

	File file;

	public TextResouce(String filePath) {
		this.file = new File(filePath);
	}

	public TextResouce(File file) {
		this.file = file;
	}

	@Override
	public DataSourceType getDataSourceType() {
		return DataSourceType.TEXT;
	}

	@Override
	public DataSource generateDataSource() {
		return new TextDataSource() {

			@Override
			public String getSourceIDName() {
				return file.getName();
			}

			@Override
			public FileReader getReader() {
				try {
					return new FileReader(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				return null;
			}
		};
	}

}
