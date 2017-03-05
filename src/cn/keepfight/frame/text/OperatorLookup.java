package cn.keepfight.frame.text;

import java.util.List;

import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.chain.Resource;
import cn.keepfight.operator.AbstractOperator;

/**
 * 文本查找算子
 * @author Tom
 *
 */
public class OperatorLookup extends AbstractOperator<TextTStage>{

//	private int lineNum = -1;
//	private int rayNum = -1;

	TextTStage tStage;

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
	public List<Resource> onAction() {
		String target = "天蝎座";
		int index = tStage.getPaneVC().textArea.getText().indexOf(target);
		tStage.getPaneVC().textArea.selectRange(index, index+target.length());
		return null;
	}
	@Override
	public OperatorResource generateResource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTStage(TextTStage tStage) {
		this.tStage = tStage;
	}

}
