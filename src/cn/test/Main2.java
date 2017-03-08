package cn.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main2 extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("选择文件群");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
		List<File> targetList = fileChooser.showOpenMultipleDialog(primaryStage);
		if (targetList == null) {
			System.out.println("targetList none");
	    }
		targetList.parallelStream()
				.filter(f->{
							try {
								return Files.newBufferedReader(Paths.get(f.getPath())).lines()
										.anyMatch(line-> (line.indexOf(",")!=-1));
							} catch (IOException e) {
								e.printStackTrace();
							}
							return false;
						}
				)
				.forEach(f->System.out.println(f.getName()));

//				.flatMap(f->{
//					List<String> contList = new ArrayList<>();
//					BufferedReader bf = new BufferedReader(new FileReader(f));
//					while (bf.lines()) {
//
//					}
//					return contList;
//					return new BufferedReader(new FileReader(f)).lines();
//				})

	}

	public static void main(String[] args) {
		launch(args);
	}

}
