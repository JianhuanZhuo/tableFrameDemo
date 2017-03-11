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
	 * 由状态到某组件（菜单项）是否可用的映射，这里仅仅作某组件（菜单项）是否可用，而不做事件的处理与消费
	 */
	protected Map<TableSelect.TableSelectState, List<MenuItemController>> stateMap = new HashMap<TableSelect.TableSelectState, List<MenuItemController>>();

	/**
	 * 将状态映射至菜单项，表示某状态下菜单项可用。也就是单个菜单项可以被多个状态映射。
	 * @param controller 控制器
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
