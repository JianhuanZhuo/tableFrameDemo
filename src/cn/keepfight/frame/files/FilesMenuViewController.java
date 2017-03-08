package cn.keepfight.frame.files;

import cn.keepfight.frame.TStage;
import cn.keepfight.frame.files.operator.FilesFilterOperator;
import cn.keepfight.frame.menu.MenuItemType;
import cn.keepfight.frame.menu.MenuViewController;

public class FilesMenuViewController extends MenuViewController{

	FilesTStage tStage;

	@SuppressWarnings("rawtypes")
	@Override
	public void setTStage(TStage tStage) {
		this.tStage=(FilesTStage) tStage;
	}

	@Override
	public void addMenuItem() {
		createMenuItem(0, MenuItemType.TP_32_TOP).setOperator(new FilesFilterOperator(tStage));
	}

}
