package cn.keepfight.frame.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.keepfight.frame.TStage;
import cn.keepfight.frame.TableTStage;
import cn.keepfight.frame.menu.MenuItemController;
import cn.keepfight.frame.menu.MenuItemType;
import cn.keepfight.frame.menu.MenuViewController;
import cn.keepfight.frame.table.operator.MergeColumnOperator;
import cn.keepfight.frame.table.operator.OrderTableOperator;

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

//	/**
//	 * 为算子添加到指定索引的菜单组中
//	 * 重载方法默认使用 {@link cn.keepfight.frame.menu.MenuItemType.TP_32_TOP} 作为图标类型。
//	 * @param operatorModel 欲添加菜单项的算子。
//	 * @param groupIndex 组索引，由0开始计数
//	 * @return 菜单项对应的控制器
//	 */
//	public MenuItemController addMenuItem(AbstractOperator<TableTStage> operator, int groupIndex) {
//		return createMenuItem(operator, groupIndex, MenuItemType.TP_32_TOP);
//	}
//
//	public MenuItemController createMenuItem(AbstractOperator operator, int groupIndex, MenuItemType type) {
//		MenuItemController controller = super.createMenuItem(operator, groupIndex, type);
//		//将菜单项添加到状态可影响列表中。
//		mapState(controller);
//		return controller;
//	}


	@Override
	public void setTStage(@SuppressWarnings("rawtypes") TStage tStage) {
		this.tableTStage = (TableTStage) tStage;
	}


	@Override
	public void addMenuItem() {
		createMenuItem(0, MenuItemType.TP_32_TOP).setOperator(new OrderTableOperator(tableTStage));
		createMenuItem(0, MenuItemType.TP_32_TOP).setOperator(new MergeColumnOperator(tableTStage));
	}
}
