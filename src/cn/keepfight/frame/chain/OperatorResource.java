package cn.keepfight.frame.chain;

import cn.keepfight.frame.content.source.DataSource;

/**
 * ������Դ�������ࡣ
 * @author Tom
 *
 */
public class OperatorResource extends Resource{

	private int id;//����ID
	private String name;//������
	private String label;//����ͼ�갴ť�ϵ�����
	private String icon;//����ͼ��
	private String description;//����������Ϣ
	private String[] inputResource;//�������������Դ
	private String[] outputResource;//�������������Դ
	private String[] params;
	public String[] getInputResource() {
		return inputResource;
	}

	public void setInputResource(String[] inputResource) {
		this.inputResource = inputResource;
	}

	public String[] getOutputResource() {
		return outputResource;
	}

	public void setOutputResource(String[] outputResource) {
		this.outputResource = outputResource;
	}

	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}

	public OperatorResource(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public int getId() {
		return id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getIconURL() {
		return icon;
	}

	@Override
	public DataSource generateDataSource() {
		// TODO Auto-generated method stub
		return null;
	}
}
