package cn.keepfight.frame;

import java.io.IOException;

import cn.keepfight.frame.controller.MenuItemController;
import cn.keepfight.utils.ViewPathUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {

	GridPane rootLayout;

//	@Override
//	public void start(Stage primaryStage) {
//		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/TableMainView.fxml"));
//			rootLayout = (BorderPane)loader.load();
//			Scene scene = new Scene(rootLayout);
//			primaryStage.setScene(scene);
//			primaryStage.setTitle("表格与算子联动分析Demo");
//			primaryStage.show();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	public void start(Stage primaryStage) {
		try {
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/TableMainFrame.fxml"));
			FXMLLoader loader = new FXMLLoader(ViewPathUtil.getFrameView("MenuItemViewp33test.fxml"));
			loader.setController(new MenuItemController());
			rootLayout = (GridPane)loader.load();
//			AnchorPane tableMenuAnchorPane = (AnchorPane)rootLayout.lookup("#tableMenu");
//			AnchorPane.setRightAnchor(tableMenuAnchorPane, 0.0);
//			AnchorPane.setLeftAnchor(tableMenuAnchorPane, 0.0);
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.setTitle("表格与算子联动分析Demo");
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
