package cn.keepfight.operator;

import java.util.Optional;

import cn.keepfight.utils.ImageLoadUtil;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class WaitDialog<V> extends Dialog<Boolean> implements EventHandler<WorkerStateEvent>{

	public static final String waitIcon = "wait.png";

	private Task<V> task;

	public WaitDialog(Task<V> task) {
		setTitle("����ִ��");
		setHeaderText("�������������Ҫ����Щʱ�䣬���Եȴ�...");
		setGraphic(new ImageView(ImageLoadUtil.load(waitIcon, ImageLoadUtil.IMG_SIZE_64)));

		ButtonType buttonTypeCancel = new ButtonType("ȡ��", ButtonData.CANCEL_CLOSE);

		getDialogPane().getButtonTypes().addAll(buttonTypeCancel);

		ProgressIndicator indic = new ProgressIndicator();
		BorderPane borderPane = new BorderPane(indic);

		getDialogPane().setContent(borderPane);

		Stage stage = (Stage) getDialogPane().getScene().getWindow();

		// Add a custom icon.
		stage.getIcons().add(ImageLoadUtil.load(waitIcon, ImageLoadUtil.IMG_SIZE_16));

		setResultConverter(dialogButton -> {
		    return false;
		});

		this.task = task;
	}

	public V justWait(){
		task.setOnSucceeded(this);
		new Thread(task).start();
		Optional<Boolean> result = showAndWait();
		if (result.isPresent() && result.get()) {
			return task.getValue();
		};
		return null;
	}


	@Override
	public void handle(WorkerStateEvent event) {
		setResult(Boolean.TRUE);
		close();
	}

}
