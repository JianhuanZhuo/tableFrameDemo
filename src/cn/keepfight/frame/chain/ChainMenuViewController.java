package cn.keepfight.frame.chain;

import cn.keepfight.frame.TStage;
import cn.keepfight.frame.chain.operator.OpenFilesOperaor;
import cn.keepfight.frame.chain.operator.OpenTextOperaor;
import cn.keepfight.frame.menu.MenuItemType;
import cn.keepfight.frame.menu.MenuViewController;

public class ChainMenuViewController extends MenuViewController{

	ChainTStage chainTStage;

	@Override
	public void setTStage(@SuppressWarnings("rawtypes") TStage tStage) {
		this.chainTStage = (ChainTStage) tStage;
	}

	@Override
	public void addMenuItem() {
		createMenuItem(0, MenuItemType.TP_32_TOP).setOperator(new OpenTextOperaor(chainTStage));
		createMenuItem(0, MenuItemType.TP_32_TOP).setOperator(new OpenFilesOperaor(chainTStage));
	}

}
