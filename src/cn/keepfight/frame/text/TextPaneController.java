package cn.keepfight.frame.text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import cn.keepfight.frame.PaneController;
import cn.keepfight.frame.TStage;
import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.content.source.InvalidSourceException;
import cn.keepfight.operator.WaitDialog;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class TextPaneController extends PaneController{

	@FXML
	BorderPane pane;

	@FXML Button pageup;
	@FXML Button pagedown;
	@FXML TextField toPageField;
	@FXML Label totalPages;
	@FXML TextField numPerPageField;
	@FXML TextArea textArea;

	TextDataSource source;
	private TextTStage tStage;

	@Override
	public void clearContent() {
		toPageField.clear();
		totalPages.setText("/0ҳ");
		numPerPageField.clear();
		textArea.clear();
	}

	@Override
	public BorderPane getNode() {
		return pane;
	}

	@Override
	public void load() {
		new WaitDialog<Boolean>(new Task<Boolean>() {
			@Override
			protected Boolean call() throws Exception {
				String line;
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(source.getFile()), "UTF-8"));){
					line = reader.readLine();
					for (int i = 0; i < 300 && line!=null; i++) {
						textArea.appendText(line+"\n");
						line = reader.readLine();
					}
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
				return true;
			}
		}).justWait();
//		BufferedReader reader = new BufferedReader(source.getReader());
//		String line;
//		try {
//			line = reader.readLine();
//			while (line!=null) {
//				textArea.appendText(line+"\n");
//				line = reader.readLine();
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	@Override
	public void setDataSource(DataSource source) throws InvalidSourceException {
		if (!(source instanceof TextDataSource)) {
			throw new InvalidSourceException("source is not instanceof TableDataSource!");
		}
		this.source = (TextDataSource) source;
	}


	@Override
	@SuppressWarnings("rawtypes")
	public void setTStage(TStage tStage) {
		this.tStage = (TextTStage) tStage;
	}

	public TextTStage getTStage() {
		return tStage;
	}
}
