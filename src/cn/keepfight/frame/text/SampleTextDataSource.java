package cn.keepfight.frame.text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SampleTextDataSource extends TextDataSource {

	File file;

	public SampleTextDataSource(Stage stage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("打开文件");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
		file = fileChooser.showOpenDialog(stage);
		if (file == null) {
			 //@TODO 错误
			 System.err.println("file choosen is null!");
        }
	}

	@Override
	public String getSourceIDName() {
		return file.getName();
	}
	@Override
	public File getFile() {
		return file;
	}


}
