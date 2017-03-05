package cn.keepfight.frame.text;

import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.operator.AbstractOperator;

/**
 * �ı���������
 * @author Tom
 *
 */
public class OperatorLookup extends AbstractOperator{

//	private int lineNum = -1;
//	private int rayNum = -1;


	/**
	 * �ı�������ͼ������
	 */
	private TextPaneController pane;

	/**
	 * ������캯��Ҫ���û��������롢����ͻ��������л�������������룬��������������������ǻ���
	 * @TODO �������Ҳ����Ҫ������á�
	 *
	 * @param paneController
	 */
	public OperatorLookup(TextPaneController paneController) {
		pane = paneController;
	}

	private final int id = 222;
	private final String name = "lookup";
	private final String label = "����";
	private final String icon = "lookup.png";
	private final String tips = "����ָ�����ַ���";
	private final String description = "lookup";

	@Override public int getId() { return id; }
	@Override public String getName() { return name; }
	@Override public String getLabel() { return label; }
	@Override public String getIcon() { return icon; }
	@Override public String getTips() { return tips; }
	@Override public String getDescription() { return description; }

	@Override
	public void onAction() {
		String target = "��Ы��";
		int index = pane.textArea.getText().indexOf(target);
		pane.textArea.selectRange(index, index+target.length());
	}
	@Override
	public OperatorResource generateResource() {
		// TODO Auto-generated method stub
		return null;
	}

}
