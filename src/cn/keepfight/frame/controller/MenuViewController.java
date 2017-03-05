package cn.keepfight.frame.controller;

import java.io.IOException;

import cn.keepfight.frame.TStage;
import cn.keepfight.frame.TableTStage;
import cn.keepfight.frame.menu.MenuItemType;
import cn.keepfight.operator.AbstractOperator;
import cn.keepfight.utils.ViewPathUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * �˵���ͼ��������<br/>
 * �������ǳ����࣬�����޷�ֱ����Ϊ
 * @author Tom
 *
 * @param <T> T������ʾ�˵���ͼ״̬����Ҫ�������Ӳ˵���ͼ�������еõ�����ʵ��
 */
public abstract class MenuViewController{

	@FXML
	TabPane tabPane;

	/**
	 * Ϊ������ӵ�ָ�������Ĳ˵�����
	 * ���ط���Ĭ��ʹ�� {@link cn.keepfight.frame.menu.MenuItemType.TP_32_TOP} ��Ϊͼ�����͡�
	 * @param operatorModel ����Ӳ˵�������ӡ�
	 * @param groupIndex ����������0��ʼ��������󲻳�����ǰ������
	 * @param type ָ�������˵�����ʹ�õ���ͼģ��
	 * @return �˵����Ӧ�Ŀ�������������null
	 */
	public MenuItemController addMenuItem(@SuppressWarnings("rawtypes") AbstractOperator operator, int groupIndex, MenuItemType type) {
		if (operator==null || groupIndex<0 || groupIndex >= tabPane.getTabs().size()) {
			return null;
		}

		FXMLLoader loader = ViewPathUtil.getLoader(type.getViewURL());
		try {
			GridPane gridPane = loader.load();
			HBox hBox = (HBox)tabPane.getTabs().get(groupIndex).getContent();
			hBox.getChildren().add(gridPane);

			MenuItemController controller = loader.getController();

			// �������ͺ�����
			controller.setMenuItemType(type);
			controller.setOperator(operator);

			// ����ͼ��������
			controller.setPic(operator.getIcon());
			controller.setBtnText(operator.getLabel());;
			controller.setTipText(operator.getTips());

			return controller;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}


	public TabPane getNode() {
		return tabPane;
	}

	@SuppressWarnings("rawtypes")
	public abstract void setTStage(TStage tStage);

}
