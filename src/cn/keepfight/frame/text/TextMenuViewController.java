package cn.keepfight.frame.text;

import cn.keepfight.frame.TStage;
import cn.keepfight.frame.menu.MenuItemType;
import cn.keepfight.frame.menu.MenuViewController;

public class TextMenuViewController extends MenuViewController{

	TextTStage tStage;

	@Override
	@SuppressWarnings("rawtypes")
	public void setTStage(TStage tStage) {
		this.tStage = (TextTStage) tStage;
	}

	@Override
	public void addMenuItem() {
		createMenuItem(0, MenuItemType.TP_32_TOP).setOperator(new OperatorTranslate(tStage));
	}
}
