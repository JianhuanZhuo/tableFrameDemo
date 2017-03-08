package cn.keepfight.frame.files;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class TextFilesDataSource extends FilesDataSource {

	String sourceName;

	Set<File> files = new HashSet<>();

	public TextFilesDataSource(String sourceName) {
		this.sourceName = sourceName;
	}

	@Override
	public String getSourceIDName() {
		return sourceName;
	}

	@Override
	public Set<File> getFiles() {
		return files;
	}

	@Override
	public void setFiles(Set<File> files) {
		this.files = files;
	}

}
