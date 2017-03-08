package cn.keepfight.frame;

import java.io.IOException;

import cn.keepfight.frame.chain.SampleChainDataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {


	@Override
	public void start(Stage primaryStage) {
		try {
//			FrameFactory.generateBySource(new SampleTableDataSource()).show();
			FrameFactory.generateBySource(null, new SampleChainDataSource("行为信息分析链")).show();
//			FrameFactory.generateBySource(new SampleOperatorDataSource()).show();
//			FrameFactory.generateBySource(new SampleTextDataSource(primaryStage)).show();
		} catch (InvalidSourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



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
