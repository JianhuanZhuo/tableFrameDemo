package cn.keepfight.frame;

import cn.keepfight.frame.operator.OperatorDataSource;
import cn.keepfight.frame.operator.OperatorMenuViewController;
import cn.keepfight.frame.operator.OperatorPaneController;

public class OperatorTStage extends TStage<OperatorDataSource,
			OperatorMenuViewController, OperatorPaneController> {


	@Override
	protected void fixAfter() {
	}

	@Override
	public void onDelete() {
		this.close();
	}
}
