package cn.keepfight.frame.text;

import java.util.Optional;

import cn.keepfight.frame.TStage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class TextTStage extends TStage<TextDataSource, TextMenuViewController, TextPaneController> {

	@Override
	protected void fixAfter() {
		String target = "天蝎座";
		int index = paneVC.textArea.getText().indexOf(target);
		paneVC.textArea.selectRange(index, index+target.length());
	}

	@Override
	public void onDelete() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("确认对话框");
		alert.setHeaderText("该文本资源在父面板已被删除，是否保存当前面板内容？");
		alert.setContentText("即将关闭该面板以响应保存有该文本资源的父面板，"
				+ "是否保存该面板的内容以免正在编辑的数据发生丢失？"
				+ "点击 是 保存该面板数据，点击 否 不保存，直接关闭！");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			System.out.println("click OK");
		} else {
			System.out.println("click cancel");
			close();
		}
	}

}
