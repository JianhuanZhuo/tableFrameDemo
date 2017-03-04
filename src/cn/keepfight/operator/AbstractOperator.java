package cn.keepfight.operator;

/**
 * ����ģ�͵������ࡣ
 * @author Tom
 *
 */
public class AbstractOperator{

	private int id;//����ID
	private String name;//������
	private String label;//����ͼ�갴ť�ϵ�����
	private String tips;//������ʾ��Ϣ
	private String icon;//����ͼ��
	private String description;//����������Ϣ
//	private DataSourceType inputType;//��������
//	private DataSourceType outputType;//�������

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
