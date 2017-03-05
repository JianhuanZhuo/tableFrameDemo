package cn.keepfight.frame.text;

import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.operator.AbstractOperator;

/**
 * 文本查找算子
 * @author Tom
 *
 */
public class OperatorLookup extends AbstractOperator{

//	private int lineNum = -1;
//	private int rayNum = -1;


	/**
	 * 文本画板视图控制器
	 */
	private TextPaneController pane;

	/**
	 * 这个构造函数要求用户给出输入、输出和环境。其中画板控制器是输入，算子链是输出，？？？是环境
	 * @TODO 这个环境也是需要多加斟酌。
	 *
	 * @param paneController
	 */
	public OperatorLookup(TextPaneController paneController) {
		pane = paneController;
	}

	private final int id = 222;
	private final String name = "lookup";
	private final String label = "查找";
	private final String icon = "lookup.png";
	private final String tips = "查找指定的字符串";
	private final String description = "lookup";

	@Override public int getId() { return id; }
	@Override public String getName() { return name; }
	@Override public String getLabel() { return label; }
	@Override public String getIcon() { return icon; }
	@Override public String getTips() { return tips; }
	@Override public String getDescription() { return description; }

	@Override
	public void onAction() {
		String target = "天蝎座";
		int index = pane.textArea.getText().indexOf(target);
		pane.textArea.selectRange(index, index+target.length());
	}
	@Override
	public OperatorResource generateResource() {
		// TODO Auto-generated method stub
		return null;
	}

}
