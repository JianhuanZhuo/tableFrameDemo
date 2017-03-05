package cn.keepfight.frame.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.keepfight.frame.TStage;
import cn.keepfight.frame.TableTStage;
import cn.keepfight.frame.menu.MenuItemType;
import cn.keepfight.operator.AbstractOperator;

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

	TableTStage tableTStage;

	/**
	 * Ϊ������ӵ�ָ�������Ĳ˵�����
	 * ���ط���Ĭ��ʹ�� {@link cn.keepfight.frame.menu.MenuItemType.TP_32_TOP} ��Ϊͼ�����͡�
	 * @param operatorModel ����Ӳ˵�������ӡ�
	 * @param groupIndex ����������0��ʼ����
	 * @return �˵����Ӧ�Ŀ�����
	 */
	public MenuItemController addMenuItem(AbstractOperator<TableTStage> operator, int groupIndex) {
		return addMenuItem(operator, groupIndex, MenuItemType.TP_32_TOP);
	}

	public MenuItemController addMenuItem(AbstractOperator operator, int groupIndex, MenuItemType type) {
		MenuItemController controller = super.addMenuItem(operator, groupIndex, type);
		//���˵�����ӵ�״̬��Ӱ���б��С�
		mapState(controller);
		return controller;
	}

	protected void addLocalMenuItems(){

	}


	@Override
	public void setTStage(@SuppressWarnings("rawtypes") TStage tStage) {
		this.tableTStage = (TableTStage) tStage;
	}
}
