package cn.keepfight.frame.content.source;

/**
 * ����Դ����ö�٣�������������ڿ���Ҳ���������
 * @author Tom
 *
 */
public enum DataSourceType {

	/**
	 *�����������
	 */
	TABLE("���", new ViewPool("TableMenuView.fxml"), new ViewPool("TablePanel.fxml")),

	/**
	 * ͼƬ��������
	 */
	PICTURE("ͼƬ", new ViewPool(), new ViewPool()),
	VIDEO("��Ƶ", new ViewPool(), new ViewPool()),     // ��Ƶ��������
	TEXT("�ı�", new ViewPool(), new ViewPool()),      // TEXT�ı��ļ���������
	FILE("�ļ�", new ViewPool(), new ViewPool()),      // �ļ���������
	URL("����", new ViewPool(), new ViewPool()),       // URL��������
	DEVICE("�豸", new ViewPool(), new ViewPool()),    // �豸��������
	CODE("����", new ViewPool(), new ViewPool()),      // ������������
	OPERATOR("����", new ViewPool(), new ViewPool()),  // �����������ͣ�Ҳ���Ǵ��ڰ����ӵ�����һ���������͵Ŀ��ܣ����ֿ�����Ϊ�鿴ĳ���Ӷ�׼���ġ�
	OPERATORCHAIN("������", new ViewPool(), new ViewPool()),// �������������ͣ�����Ϊ����������׼���ġ�
	OTHER("����", new ViewPool(), new ViewPool());      // ������������

	private String typeName_cn;
	private ViewPool menuPool;//�˵�����ͼ��
	private ViewPool contentPool;//���ݵ���ͼ��


	private DataSourceType(String typeName_cn, ViewPool menuPool, ViewPool contentPool) {
		this.typeName_cn = typeName_cn;
		this.menuPool = menuPool;
		this.contentPool = contentPool;
	}

	public String getTypeName_cn() {
		return typeName_cn;
	}

	public ViewPool getMenuViewPool() {
		return menuPool;
	}

	public ViewPool getContentViewPool() {
		return contentPool;
	}
}
