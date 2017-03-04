package cn.keepfight.frame.chain;

import cn.keepfight.frame.content.source.DataSource;

/**
 * 算子资源的描述类。
 * @author Tom
 *
 */
public class OperatorResource extends Resource{

	private int id;//算子ID
	private String name;//算子名
	private String label;//算子图标按钮上的名字
	private String icon;//算子图标
	private String description;//算子描述信息
	private String[] inputResource;//运算输入对象资源
	private String[] outputResource;//运算输出对象资源
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
