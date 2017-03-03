package cn.keepfight.frame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.keepfight.frame.content.source.InvalidSourceException;
import cn.keepfight.frame.content.source.TableDataSource;
import cn.keepfight.frame.controller.TableMenuViewController;
import cn.keepfight.utils.ViewPathUtil;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;


public class Main extends Application {


	@Override
	public void start(Stage primaryStage) {
		try {
//			BorderPane rootLayout;
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/TableMainView.fxml"));
//			rootLayout = (BorderPane)loader.load();
//			Scene scene = new Scene(rootLayout);
//			primaryStage.setScene(scene);
//			primaryStage.setTitle("表格与算子联动分析Demo");
//			primaryStage.show();
			System.out.println("xx");

			TableDataSource source = new SampleTableDataSource();
			TableTStage tStage = new TableTStage();
			tStage.InitSource(source);
			tStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidSourceException e) {
			e.printStackTrace();
		}
	}



//	@Override
//	public void start(Stage primaryStage) {
//		try {
////			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/TableMainFrame.fxml"));
//			FXMLLoader loader = new FXMLLoader(ViewPathUtil.getFrameView("MenuItemViewp33test.fxml"));
//			loader.setController(new MenuItemController());
//			rootLayout = (GridPane)loader.load();
////			AnchorPane tableMenuAnchorPane = (AnchorPane)rootLayout.lookup("#tableMenu");
////			AnchorPane.setRightAnchor(tableMenuAnchorPane, 0.0);
////			AnchorPane.setLeftAnchor(tableMenuAnchorPane, 0.0);
//			Scene scene = new Scene(rootLayout);
//			primaryStage.setScene(scene);
//			primaryStage.setTitle("表格与算子联动分析Demo");
//			primaryStage.show();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

//	@Override
//	public void start(Stage primaryStage) {
//		try {
////			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/TableMainFrame.fxml"));
//			FXMLLoader loader = new FXMLLoader(ViewPathUtil.getFrameView("TableMenuView.fxml"));
//			loader.setController(new TableMenuViewController());
//			rootLayout = loader.load();
////			AnchorPane tableMenuAnchorPane = (AnchorPane)rootLayout.lookup("#tableMenu");
////			AnchorPane.setRightAnchor(tableMenuAnchorPane, 0.0);
////			AnchorPane.setLeftAnchor(tableMenuAnchorPane, 0.0);
//			Scene scene = new Scene(rootLayout);
//			primaryStage.setScene(scene);
//			primaryStage.setTitle("表格与算子联动分析Demo");
//			primaryStage.show();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public static void main(String[] args) {
		launch(args);
	}
}
