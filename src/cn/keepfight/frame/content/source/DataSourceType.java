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
	TABLE("���", 	new ViewPool("TablePane.fxml"), 	"table.png"),

	/**
	 * ͼƬ��������
	 */
	PICTURE("ͼƬ", 	new ViewPool(), 					"picture.png"),
	VIDEO("��Ƶ", 	new ViewPool(), 					"video.png"),     // ��Ƶ��������
	TEXT("�ı�",  	new ViewPool("TextPane.fxml"),	 	"text.png"),      // TEXT�ı��ļ���������
	FILE("�ļ�", 	new ViewPool(), 					"file.png"),      // �ļ���������
	FILES("�ļ�Ⱥ", 	new ViewPool("FilesPane.fxml"), 	"files.png"),     // �ļ�Ⱥ��������
	URL("����", 		new ViewPool(), 					"URL.png"),       // URL��������
	DEVICE("�豸", 	new ViewPool(), 					"device.png"),    // �豸��������
	CODE("����", 	new ViewPool(), 					"code.png"),      // ������������
	OPERATOR("����", new ViewPool("OperatorPane.fxml"), 	"operator.png"),  // �����������ͣ�Ҳ���Ǵ��ڰ����ӵ�����һ���������͵Ŀ��ܣ����ֿ�����Ϊ�鿴ĳ���Ӷ�׼���ġ�
	CHAIN("������",	new ViewPool("ChainPane.fxml"), 	"operatorchain.png"),// �������������ͣ�����Ϊ����������׼���ġ�
	OTHER("����", 	new ViewPool(), 					"other.png");      // ������������

	private String typeName_cn;
	private ViewPool contentPool;//���ݵ���ͼ��
	private String iconURL;//ͼ��URL


	private DataSourceType(String typeName_cn, ViewPool contentPool, String iconURL) {
		this.typeName_cn = typeName_cn;
		this.contentPool = contentPool;
		this.iconURL = iconURL;
	}

	public String getTypeName_cn() {
		return typeName_cn;
	}

	public ViewPool getContentViewPool() {
		return contentPool;
	}

	public String getIconURL() {
		return iconURL;
	}
}
