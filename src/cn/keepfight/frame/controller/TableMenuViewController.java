package cn.keepfight.frame.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.menu.MenuItemType;
import javafx.fxml.FXML;

public class TableMenuViewController extends MenuViewController{

	/**
	 * ��״̬��ĳ������˵���Ƿ���õ�ӳ�䣬���������ĳ������˵���Ƿ���ã��������¼��Ĵ���������
	 */
	protected Map<TableSelectState, List<MenuItemController>> stateMap = new HashMap<TableSelectState, List<MenuItemController>>();

	/**
	 * ��״̬ӳ�����˵����ʾĳ״̬�²˵�����á�Ҳ���ǵ����˵�����Ա����״̬ӳ�䡣
	 * @param controller ������
	 */
	protected void mapState(MenuItemController controller) {
	}

	/**
	 * Ϊ������ӵ�ָ�������Ĳ˵�����
	 * ���ط���Ĭ��ʹ�� {@link cn.keepfight.frame.menu.MenuItemType.TP_32_TOP} ��Ϊͼ�����͡�
	 * @param operatorModel ����Ӳ˵�������ӡ�
	 * @param groupIndex ����������0��ʼ����
	 * @return �˵����Ӧ�Ŀ�����
	 */
	public MenuItemController addMenuItem(OperatorResource operatorModel, int groupIndex) {
		return addMenuItem(operatorModel, groupIndex, MenuItemType.TP_32_TOP);
	}

	@Override
	public MenuItemController addMenuItem(OperatorResource operatorModel, int groupIndex, MenuItemType type) {
		MenuItemController controller = super.addMenuItem(operatorModel, groupIndex, type);
		//���˵�����ӵ�״̬��Ӱ���б��С�
		mapState(controller);
		return controller;
	}

	@FXML
	public void initialize(){
		addLocalMenuItems();
	}

	private void addLocalMenuItems(){

	}
}
