package cn.keepfight.frame.text;

import java.util.ArrayList;
import java.util.List;

import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.frame.chain.Resource;
import cn.keepfight.operator.AbstractOperator;

public class OperatorTranslate extends AbstractOperator<TextTStage>{

	private int id;//����ID
	private String name;//������
	private String label;//����ͼ�갴ť�ϵ�����
	private String tips;//������ʾ��Ϣ
	private String icon;//����ͼ��
	private String description;//����������Ϣ

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
		//@TODO ������Դ

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
