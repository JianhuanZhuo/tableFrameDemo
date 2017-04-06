package cn.keepfight.frame.menu;

public enum MenuItemType {

	P_32(true, false, 32, "MenuItemViewp32.fxml"),//仅带32位图片
	TP_16(true, true, 16, "MenuItemViewtp16.fxml"),//带图片文字的
	TP_32(true, true, 32, "MenuItemViewtp32.fxml"),//带图片文字的
	TP_32_TOP(true, true, 32, "MenuItemViewtp32-top.fxml"),//带图片文字的
	UNKNOW(false, false, -1, "MenuItemViewp32.fxml");//未知类型

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
	 * 该类型是否带图片
	 * @return 带图片返回true，否则返回false。
	 */
	public boolean hasPicture() {
		return hasPicture;
	}

	/**
	 * 该类型是否带文字
	 * @return 带文字返回true，否则返回false。
	 */
	public boolean hasText() {
		return hasText;
	}

	/**
	 * 该类型图片的尺寸大小
	 * @return 若该类型存在图片则返回该类型图片的尺寸大小，否则返回-1。
	 */
	public int picSize() {
		return (hasPicture()?this.picSize:-1);
	}


	/**
	 * 获得视图URL
	 * @return 该类型的视图URL
	 */
	public String getViewURL() {
		return viewURL;
	}
}
