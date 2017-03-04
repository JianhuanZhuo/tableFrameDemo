package cn.keepfight.operator;

/**
 * 算子模型的描述类。
 * @author Tom
 *
 */
public class AbstractOperator{

	private int id;//算子ID
	private String name;//算子名
	private String label;//算子图标按钮上的名字
	private String tips;//算子提示信息
	private String icon;//算子图标
	private String description;//算子描述信息
//	private DataSourceType inputType;//输入类型
//	private DataSourceType outputType;//输出类型

	public AbstractOperator(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public static AbstractOperator demoOperator() {
		return new AbstractOperator(-1, "");
	}

	public String getName() {
		return name;
	}
	public String getIcon() {
		return icon;
	}
	public String getTips() {
		return tips;
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
	public void setTips(String tips) {
		this.tips = tips;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
