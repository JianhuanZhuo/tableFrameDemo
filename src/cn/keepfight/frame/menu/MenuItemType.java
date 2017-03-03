package cn.keepfight.frame.menu;

public enum MenuItemType {

	P_32(true, false, 32, "MenuItemViewp32.fxml"),//����32λͼƬ
	TP_16(true, true, 16, "MenuItemViewtp16.fxml"),//��ͼƬ���ֵ�
	TP_32(true, true, 32, "MenuItemViewtp32.fxml"),//��ͼƬ���ֵ�
	TP_32_TOP(true, true, 32, "MenuItemViewtp32-top.fxml"),//��ͼƬ���ֵ�
	UNKNOW(false, false, -1, "MenuItemViewp32.fxml");//δ֪����

	private boolean hasPicture;
	private boolean hasText;
	private int picSize;
	private String viewURL;

	MenuItemType(boolean hasPicture, boolean hasText, int picSize, String viewURL){
		this.hasPicture = hasPicture;
		this.hasText = hasText;
		this.picSize = picSize;
		this.viewURL = viewURL;
	}


	/**
	 * �������Ƿ��ͼƬ
	 * @return ��ͼƬ����true�����򷵻�false��
	 */
	public boolean hasPicture() {
		return hasPicture;
	}

	/**
	 * �������Ƿ������
	 * @return �����ַ���true�����򷵻�false��
	 */
	public boolean hasText() {
		return hasText;
	}

	/**
	 * ������ͼƬ�ĳߴ��С
	 * @return �������ʹ���ͼƬ�򷵻ظ�����ͼƬ�ĳߴ��С�����򷵻�-1��
	 */
	public int picSize() {
		return (hasPicture()?this.picSize:-1);
	}


	/**
	 * �����ͼURL
	 * @return �����͵���ͼURL
	 */
	public String getViewURL() {
		return viewURL;
	}
}
