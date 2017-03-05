package cn.keepfight.frame.operator;

import cn.keepfight.frame.OperatorTStage;
import cn.keepfight.frame.TStage;
import cn.keepfight.frame.controller.MenuViewController;

public class OperatorMenuViewController extends MenuViewController{

	OperatorTStage operatorTStage;

	@Override
	public void setTStage(@SuppressWarnings("rawtypes") TStage tStage) {
		this.operatorTStage = (OperatorTStage) tStage;
	}
}
