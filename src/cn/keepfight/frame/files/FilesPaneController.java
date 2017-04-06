package cn.keepfight.frame.files;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

import cn.keepfight.frame.PaneController;
import cn.keepfight.frame.TStage;
import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
import cn.keepfight.utils.ViewPathUtil;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class FilesPaneController extends PaneController {

	/**
	 * 画板的根布局
	 */
	@FXML
	BorderPane paneRoot;

	@FXML
	FlowPane filesPane;

	@FXML
	TextField resourceURL;

	FilesDataSource filesDataSource;

	FilesTStage tStage;

	@Override
	public void clearContent() {
		filesPane.getChildren().clear();
	}

	@Override
	public BorderPane getNode() {
		return paneRoot;
	}

	@Override
	public void load() {
		clearContent();
		filesDataSource.getFiles()
			.stream()
			.map(new Function<File, Node>() {
				@Override
				public Node apply(File f) {
					FileItemController controller = null;
					try {
						controller = (FileItemController)ViewPathUtil.loadViewForController("FileItemViewtp32-top2.fxml");
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}
					controller.setText(f.getName());
					controller.setPic("file.png");
					controller.setTipText(f.getAbsolutePath());
					return controller.getPaneRoot();
				}
			})
			.forEach(node -> filesPane.getChildren().add(node));;
	}



	@Override
	public void setDataSource(DataSource source) throws InvalidSourceException {
		if (!(source instanceof FilesDataSource)) {
			throw new InvalidSourceException("source is not instanceof FilesDataSource!");
		}
		filesDataSource = (FilesDataSource) source;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setTStage(TStage tStage) {
		this.tStage = (FilesTStage) tStage;
	}

}
