package cn.keepfight.frame.text;

import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.operator.AbstractOperator;
import javafx.fxml.FXML;

public class OperatorTranslate extends AbstractOperator{

	private int id;//算子ID
	private String name;//算子名
	private String label;//算子图标按钮上的名字
	private String tips;//算子提示信息
	private String icon;//算子图标
	private String description;//算子描述信息

	private TextPaneController pane;

	/**
	 * 这个构造函数要求用户给出输入、输出和环境。其中画板控制器是输入，算子链是输出，？？？是环境
	 * @TODO 这个环境也是需要多加斟酌。
	 *
	 * @param paneController
	 */
	public OperatorTranslate(TextPaneController paneController) {
		pane = paneController;
	}

	@Override public int getId() { return id; }
	@Override public String getName() { return name; }
	@Override public String getLabel() { return label; }
	@Override public String getIcon() { return icon; }
	@Override public String getTips() { return tips; }
	@Override public String getDescription() { return description; }

	@Override
	public void onAction() {

	}

	@Override
	public OperatorResource generateResource() {
		// TODO Auto-generated method stub
		return null;
	}

}
