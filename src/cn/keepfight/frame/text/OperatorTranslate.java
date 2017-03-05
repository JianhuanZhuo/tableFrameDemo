package cn.keepfight.frame.text;

import cn.keepfight.frame.chain.OperatorResource;
import cn.keepfight.operator.AbstractOperator;
import javafx.fxml.FXML;

public class OperatorTranslate extends AbstractOperator{

	private int id;//����ID
	private String name;//������
	private String label;//����ͼ�갴ť�ϵ�����
	private String tips;//������ʾ��Ϣ
	private String icon;//����ͼ��
	private String description;//����������Ϣ

	private TextPaneController pane;

	/**
	 * ������캯��Ҫ���û��������롢����ͻ��������л�������������룬��������������������ǻ���
	 * @TODO �������Ҳ����Ҫ������á�
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
