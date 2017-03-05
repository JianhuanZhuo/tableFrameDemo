package cn.keepfight.frame.text;

import java.util.Optional;

import cn.keepfight.frame.TStage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class TextTStage extends TStage<TextDataSource, TextMenuViewController, TextPaneController> {

	@Override
	protected void fixAfter() {
		String target = "��Ы��";
		int index = paneVC.textArea.getText().indexOf(target);
		paneVC.textArea.selectRange(index, index+target.length());
	}

	@Override
	public void onDelete() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("ȷ�϶Ի���");
		alert.setHeaderText("���ı���Դ�ڸ�����ѱ�ɾ�����Ƿ񱣴浱ǰ������ݣ�");
		alert.setContentText("�����رո��������Ӧ�����и��ı���Դ�ĸ���壬"
				+ "�Ƿ񱣴�����������������ڱ༭�����ݷ�����ʧ��"
				+ "��� �� �����������ݣ���� �� �����棬ֱ�ӹرգ�");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			System.out.println("click OK");
		} else {
			System.out.println("click cancel");
			close();
		}
	}

}
