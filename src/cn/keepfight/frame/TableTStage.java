package cn.keepfight.frame;

import java.util.Optional;

import cn.keepfight.frame.content.source.DataSource;
import cn.keepfight.frame.table.TableDataSource;
import cn.keepfight.frame.table.TableMenuViewController;
import cn.keepfight.frame.table.TablePaneController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * ������
 * @author Tom
 *
 */
public class TableTStage extends TStage<TableDataSource, TableMenuViewController,
			TablePaneController>{

	@Override
	protected void fixAfter() {
	}

	@Override
	public void onDelete() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("ȷ�϶Ի���");
		alert.setHeaderText("�ñ����Դ�ڸ�����ѱ�ɾ�����Ƿ񱣴浱ǰ������ݣ�");
		alert.setContentText("�����رո��������Ӧ�����иñ����Դ�ĸ���壬"
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
