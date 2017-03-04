package cn.keepfight.frame.operator;

public class SampleOperatorDataSource extends OperatorDataSource{

	@Override
	public String getSourceIDName() {
		return label+"-"+name+"-"+id;
	}

	private int id = 123;//����ID
	private String name = "filter";//������
	private String label = "����ɸѡ";//����ͼ�갴ť�ϵ�����
	private String icon = "filter.png";//����ͼ��
	private String description =
			"Excelɸѡ ��һ������excel�ļ��в�ͬsheet�е����ݽ��кϲ�,"
			+ "�Ƚ��Ƿ��ظ������ ���ճ��������������ǳ���excel���ӱ������¼����,"
			+ "��ʱ����������Ҫ֪����������ݺ�����,����ȥ���ظ���,"
			+ "����,�ڶ�����������Щ�ڵ�һ�����Ѿ�����.�����ƶ���˾��Ա��,"
			+ "Ҫ֪����Щ�����ظ�,���߽��������ϲ�����ȥ���ظ��ļ�¼."
			+ "���ڿ�����Ա,����,ϵͳ����Ա,��˵��ʡȥ�ܶ��ʱ����߼��ٿ��ܳ��ֵĴ���."
			+ "Ŀǰ��������ʹ���� Win2000/Win2003/Winxp/Vista ƽ̨��,�޲��!"
			+ "�������ȫ���!";//����������Ϣ
	private String[] inputResource = {"wz.tp_info"};//�������������Դ
	private String[] outputResource = {"wz.tp_info_view_1"};//�������������Դ
	private String[] params = {"mode=1", "by=,&."};

	@Override
	public String[] getInputResource() {
		return inputResource;
	}

	public void setInputResource(String[] inputResource) {
		this.inputResource = inputResource;
	}

	@Override
	public String[] getOutputResource() {
		return outputResource;
	}

	@Override
	public String[] getParams() {
		return params;
	}

	@Override
	public String getName() {
		return name;
	}


	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getIcon() {
		return icon;
	}
}
