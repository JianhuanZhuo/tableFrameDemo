package cn.keepfight.frame.text;

import java.util.ArrayList;
import java.util.List;

import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.chain.Resource;
import cn.keepfight.operator.AbstractOperator;

public class OperatorTranslate extends AbstractOperator<TextTStage>{

	private int id;//算子ID
	private String name;//算子名
	private String label;//算子图标按钮上的名字
	private String tips;//算子提示信息
	private String icon;//算子图标
	private String description;//算子描述信息

	private TextTStage tStage;

	@Override public int getId() { return id; }
	@Override public String getName() { return name; }
	@Override public String getLabel() { return label; }
	@Override public String getIcon() { return icon; }
	@Override public String getTips() { return tips; }
	@Override public String getDescription() { return description; }

	@Override
	public List<Resource> onAction() {
		List<Resource> res = new ArrayList<Resource>();
		//@TODO 生成资源

		return res;
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
