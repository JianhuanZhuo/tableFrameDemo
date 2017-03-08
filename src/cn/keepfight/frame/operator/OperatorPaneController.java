package cn.keepfight.frame.operator;

import java.util.Arrays;

import cn.keepfight.frame.OperatorTStage;
import cn.keepfight.frame.PaneController;
import cn.keepfight.frame.TStage;
import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
import cn.keepfight.utils.ImageLoadUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class OperatorPaneController extends PaneController {

	@FXML
	BorderPane pane;

	@FXML
	ImageView icon;

	@FXML
	TextField oper_id;

	@FXML
	TextField oper_label;

	@FXML
	TextField oper_name;

	@FXML
	ListView<String> input_res;

	@FXML
	ListView<String> out_res;

	@FXML
	ListView<String> params;

	@FXML
	private TextArea description;


	private OperatorTStage tStage;

	OperatorDataSource source;

	@Override
	public void clearContent() {
		Image image = ImageLoadUtil.load("error.png", ImageLoadUtil.IMG_SIZE_64);
		if (image!=null) {
			icon.setImage(image);
		}
		oper_id.clear();
		oper_label.clear();
		oper_name.clear();
		input_res.getItems().clear();
		out_res.getItems().clear();
		description.clear();
		params.getItems().clear();
	}

	@Override
	public BorderPane getNode() {
		return pane;
	}

	@Override
	public void load() {
		icon.setImage(ImageLoadUtil.load(source.getIcon(), ImageLoadUtil.IMG_SIZE_64));
		oper_id.setText("" + source.getId());
		oper_label.setText("" + source.getLabel());
		oper_name.setText("" + source.getName());
		description.setText(source.getDescription());
		input_res.setItems(FXCollections.observableArrayList(Arrays.asList(source.getInputResource()==null?new String[0]:source.getInputResource())));
		out_res.setItems(FXCollections.observableArrayList(Arrays.asList(source.getOutputResource()==null?new String[0]:source.getOutputResource())));
		params.setItems(FXCollections.observableArrayList(Arrays.asList(source.getParams()==null?new String[0]:source.getParams())));
	}
	@Override
	public void setDataSource(DataSource source) throws InvalidSourceException {
		source.checkValid();
		if (!(source instanceof OperatorDataSource)) {
			throw new InvalidSourceException("source is not instanceof OperatorResource!");
		}
		this.source = (OperatorDataSource) source;

	}

	@Override
	@SuppressWarnings("rawtypes")
	public void setTStage(TStage tStage) {
		this.tStage = (OperatorTStage) tStage;
	}

	@FXML
	public void onClose() {
		//@TODO œÏ”¶πÿ±’
	}

}
