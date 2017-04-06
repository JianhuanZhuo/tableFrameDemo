package cn.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cn.keepfight.frame.connect.db.JDBCConnector;
import cn.keepfight.operator.WaitDialog;
import cn.keepfight.utils.PropertieUtil;
import cn.keepfight.utils.SimpleSQLUtils;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.sf.json.JSONArray;

public class Main2 extends Application {


	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {

//		FileChooser fileChooser = new FileChooser();
//		fileChooser.setTitle("选择文件群");
//		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
//		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
//		List<File> targetList = fileChooser.showOpenMultipleDialog(primaryStage);
//		if (targetList == null) {
//			System.out.println("targetList none");
//		}
//		long a = System.currentTimeMillis();
//		System.out.println("a:" + a);
//		long s = targetList.parallelStream().map(f -> {
//			try (BufferedReader br = new BufferedReader(new FileReader(f))) {
//				return br.lines().parallel().filter(line -> line.contains("鞋")).count();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			return 0;
//		}).count();
//
//		System.out.println(s);
//		long b = System.currentTimeMillis();
//		System.out.println("b:" + b);
//		System.out.println("a->b: " + (b - a));

		// .flatMap(f->{
		// List<String> contList = new ArrayList<>();
		// BufferedReader bf = new BufferedReader(new FileReader(f));
		// while (bf.lines()) {
		//
		// }
		// return contList;
		// return new BufferedReader(new FileReader(f)).lines();
		// })

//		System.out.println("result:"+new WaitDialog<String>(new Task<String>() {
//			@Override
//			protected String call() throws Exception {
//				long x = System.currentTimeMillis();
//				long y = System.currentTimeMillis();
//				while (y-x<5000) {
//					y = System.currentTimeMillis();
//				}
//				return "finish!";
//			}
//		}).justWait());

//		java.sql.Connection con = new JDBCConnector().getConnection();
//		for (int i = 0; i < 5; i++) {
//			long x = System.currentTimeMillis();
////			System.out.println("x:"+x);
//			long z = System.currentTimeMillis();
//			System.out.println("z-x:"+(z-x));
////			PreparedStatement pst = con.prepareStatement("select * from combine.ttee limit 10");
////			ResultSet res = pst.executeQuery();
//			ResultSet res = con.createStatement().executeQuery("select * from combine.ttee limit 10");
//			while (res.next()) {
//				//System.out.println(res.getString(1));
//			}
//			res.close();
////			con.close();
//			long y = System.currentTimeMillis();
////			System.out.println("y:"+y);
//			System.out.println("y-x:"+(y-x));
//			System.out.println("y-z:"+(y-z));
//			System.out.println("");
//		}
//		SimpleSQLUtils simpleSQL = SimpleSQLUtils.build(new JDBCConnector().getConnection()).setDB("wz");
//		for (int i = 0; i < 5; i++) {
//			long x = System.currentTimeMillis();
//			simpleSQL.select("出行数据", 0, 100);
//			long y = System.currentTimeMillis();
//////		System.out.println("y:"+y);
//			System.out.println("y-x:"+(y-x));
//		}
//
//		FileChooser fileChooser = new FileChooser();
//		String filepath = "D:/iie_learning/testfile/goods_sale.csv";
//		File file = fileChooser.showOpenDialog(primaryStage);
//		BufferedReader bf = new BufferedReader(new FileReader(file));
//
//		String head = bf.readLine();
//		bf.close();
//		List<String> fs = Arrays.asList(head.split(",")).stream()
//				.map(s->(s+" varchar(255)"))
//				.collect(Collectors.toList());
//		String tableName = "xxx123326";
//		simpleSQL.createTable(tableName, fs, null);
//		filepath = file.getAbsolutePath();
//		System.out.println(filepath);
//
//		long b = System.currentTimeMillis();
//		simpleSQL.loadData(filepath, tableName, " FIELDS TERMINATED by ',' IGNORE 1 LINES");
//		long a = System.currentTimeMillis();
//		System.out.println("b->a:"+(a-b));

//		JSONArray arr = new JSONArray();
//		arr.add("ss");
//		arr.add("ss");
//		arr.add("ss");
//		arr.add("ss");
//
//		JSONArray.toCollection(arr);


		List<String> list1 = Arrays.asList("x", "y");
		List<String> list2 = Arrays.asList("x", "z");
		Stream<String> s1 = list1.stream().peek(x->System.out.println("::"+x));
		System.out.println("哦");
		s1.filter(list2::contains).forEach(System.out::println);
	}

}
