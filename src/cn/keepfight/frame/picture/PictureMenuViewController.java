package cn.keepfight.frame.picture;

import cn.keepfight.frame.TStage;
import cn.keepfight.frame.menu.MenuViewController;

public class PictureMenuViewController extends MenuViewController{

	PictureTStage tStage;

	@Override
	@SuppressWarnings("rawtypes")
	public void setTStage(TStage tStage) {
		this.tStage = (PictureTStage) tStage;
	}

	@Override
	public void addMenuItem() {

	}

}
