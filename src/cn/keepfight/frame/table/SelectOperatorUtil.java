package cn.keepfight.frame.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import cn.keepfight.frame.TableTStage;
import cn.keepfight.operator.AbstractOperator;
import cn.keepfight.utils.ImageLoadUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * 列选择算子工具类
 * @author Tom
 *
 */
public class SelectOperatorUtil {

	TableTStage tStage;

	public SelectOperatorUtil(TableTStage tStage) {
		this.tStage=tStage;
	}

	/**
	 * 算子自身
	 * 文字
	 * 添加条件、数量、是否允许相同的
	 *
	 */
	public List<String> selectColumn(AbstractOperator operator, Function<List<String>, String> test, String tips){

		List<String> selectList = tStage.getPaneVC().getTableSelect().getColumnStrings();
		List<String> allList = tStage.getPaneVC().getLoader().allColumns();

		// Create the custom dialog.
		Dialog<List<String>> dialog = new Dialog<>();
		dialog.setTitle(operator.getLabel());
		dialog.setHeaderText(tips);

		// Set the icon (must be included in the project).
		dialog.setGraphic(new ImageView(ImageLoadUtil.load(operator.getIcon(), ImageLoadUtil.IMG_SIZE_64)));

		// Set the button types.
		ButtonType loginButtonOK = new ButtonType("确定", ButtonData.OK_DONE);
		ButtonType buttonTypeCancel = new ButtonType("取消", ButtonData.CANCEL_CLOSE);

		dialog.getDialogPane().getButtonTypes().addAll(loginButtonOK, buttonTypeCancel);

		ObservableList<String> options =
			    FXCollections.observableArrayList(allList);
		HBox hBox = new HBox(10);
		for (String c : selectList) {
			ComboBox<String> box = new ComboBox<>(options);
			box.getSelectionModel().select(c);
			Button del = new Button("-");
			hBox.getChildren().add(new HBox(5, box, del));
		}

		dialog.getDialogPane().setContent(hBox);

		dialog.setResultConverter(new Callback<ButtonType, List<String>>() {
			@SuppressWarnings("unchecked")
			@Override
			public List<String> call(ButtonType param) {
				List<String> reStrings = new ArrayList<>();
				if (param==loginButtonOK) {
					for (int i = 0; i < hBox.getChildren().size(); i++) {
						ComboBox<String> box = (ComboBox<String>) ((HBox)hBox.getChildren().get(i)).getChildren().get(0);
						reStrings.add(box.getSelectionModel().getSelectedItem());
					}
				}
				return reStrings;
			}
		});

		List<String> resList = new ArrayList<String>();
		Optional<List<String>> optionals =  dialog.showAndWait();
		if (optionals.isPresent()) {
			resList = optionals.get();
		}

		String resString = test.apply(resList);
		if (resString!=null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("操作错误！");
			alert.setContentText(resString);
			alert.show();
			return null;
		}
		return resList;
	}
}
