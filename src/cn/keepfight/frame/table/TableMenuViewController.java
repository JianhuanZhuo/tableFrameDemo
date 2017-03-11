package cn.keepfight.frame.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.keepfight.frame.TStage;
import cn.keepfight.frame.TableTStage;
import cn.keepfight.frame.menu.MenuItemController;
import cn.keepfight.frame.menu.MenuItemType;
import cn.keepfight.frame.menu.MenuViewController;
import cn.keepfight.frame.table.operator.ColumnStaticOperator;
import cn.keepfight.frame.table.operator.MergeColumnOperator;
import cn.keepfight.frame.table.operator.OrderTableOperator;
import cn.keepfight.frame.table.operator.QueryOperator;

public class TableMenuViewController extends MenuViewController{

	/**
	 * ��״̬��ĳ������˵���Ƿ���õ�ӳ�䣬���������ĳ������˵���Ƿ���ã��������¼��Ĵ���������
	 */
	protected Map<TableSelect.TableSelectState, List<MenuItemController>> stateMap = new HashMap<TableSelect.TableSelectState, List<MenuItemController>>();

	/**
	 * ��״̬ӳ�����˵����ʾĳ״̬�²˵�����á�Ҳ���ǵ����˵�����Ա����״̬ӳ�䡣
	 * @param controller ������
	 */
	protected void mapState(MenuItemController controller) {
	}

	TableTStage tableTStage;

	@Override
	public void setTStage(@SuppressWarnings("rawtypes") TStage tStage) {
		this.tableTStage = (TableTStage) tStage;
	}


	@Override
	public void addMenuItem() {
		createMenuItem(0, MenuItemType.TP_32_TOP).setOperator(new OrderTableOperator(tableTStage));
		createMenuItem(0, MenuItemType.TP_32_TOP).setOperator(new MergeColumnOperator(tableTStage));
		createMenuItem(0, MenuItemType.TP_32_TOP).setOperator(new QueryOperator(tableTStage));
		createMenuItem(0, MenuItemType.TP_32_TOP).setOperator(new ColumnStaticOperator(tableTStage));
	}
}
