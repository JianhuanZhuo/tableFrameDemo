package cn.keepfight.frame.text;

import java.net.URL;
import java.util.ResourceBundle;

import cn.keepfight.frame.TStage;
import cn.keepfight.frame.controller.MenuViewController;

public class TextMenuViewController extends MenuViewController{

	TextTStage tStage;

	@Override
	@SuppressWarnings("rawtypes")
	public void setTStage(TStage tStage) {
		this.tStage = (TextTStage) tStage;
	}
}
