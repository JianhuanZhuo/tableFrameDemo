package cn.keepfight.frame.picture;

import cn.keepfight.frame.PaneController;
import cn.keepfight.frame.TStage;
import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
import cn.keepfight.utils.ImageLoadUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class PicturePaneController extends PaneController{

	PictureTStage tStage;
	PictureDataSource dataSource;
	@FXML BorderPane pane;
	@FXML ImageView picture;
	@FXML Slider slider;

	@Override
	public void clearContent() {
	}

	@Override
	public BorderPane getNode() {
		return pane;
	}

	@Override
	public void load() {
		if (dataSource.getImage()!=null) {
			picture.setImage(dataSource.getImage());
		}else{
			picture.setImage(ImageLoadUtil.loadViewImage("noimagefound.jpg"));
		}

	}

	@Override
	public void setDataSource(DataSource source) throws InvalidSourceException {
		if (!(source instanceof PictureDataSource)) {
			throw new InvalidSourceException("source is not instanceof PictureDataSource");
		}
		dataSource = (PictureDataSource) source;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void setTStage(TStage tStage) {
		this.tStage = (PictureTStage) tStage;
	}

}
