package cn.keepfight.frame.chain;

import cn.keepfight.frame.TStage;
import cn.keepfight.frame.chain.operator.OpenTextOperaor;
import cn.keepfight.frame.controller.MenuViewController;
import cn.keepfight.frame.menu.MenuItemType;

public class ChainMenuViewController extends MenuViewController{

	ChainTStage chainTStage;

	@Override
	public void setTStage(@SuppressWarnings("rawtypes") TStage tStage) {
		this.chainTStage = (ChainTStage) tStage;
	}

	public void loadLocalMenu() {
		addMenuItem(new OpenTextOperaor(chainTStage), 0, MenuItemType.TP_32_TOP);
	}

}
